package br.com.will.tenant;

import java.time.Duration;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import br.com.will.dto.TenantDTO;
import br.com.will.interceptor.WebServiceException;
import br.com.will.service.TenantService;
import br.com.will.thread.FlyWayThread;
import br.com.will.thread.TenantAwareThread;
import io.agroal.api.AgroalDataSource;
import io.agroal.api.AgroalPoolInterceptor;
import io.agroal.api.configuration.AgroalConnectionPoolConfiguration;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;
import io.agroal.api.configuration.supplier.AgroalConnectionFactoryConfigurationSupplier;
import io.agroal.api.configuration.supplier.AgroalConnectionPoolConfigurationSupplier;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import io.agroal.api.security.NamePrincipal;
import io.agroal.api.security.SimplePassword;
import io.agroal.narayana.NarayanaTransactionIntegration;
import io.agroal.pool.DataSource;
import io.quarkus.agroal.runtime.AgroalConnectionConfigurer;
import io.quarkus.agroal.runtime.JdbcDriver;
import io.quarkus.arc.Arc;
import io.quarkus.arc.InstanceHandle;
import io.quarkus.arc.Unremovable;
import io.quarkus.datasource.common.runtime.DataSourceUtil;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.customized.QuarkusConnectionProvider;
import io.quarkus.hibernate.orm.runtime.tenant.TenantConnectionResolver;
import io.quarkus.logging.Log;
import jakarta.annotation.Priority;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.TransactionSynchronizationRegistry;
import jakarta.transaction.Transactional;

/**
 * This class provides a TenantConnectionResolver implementation using
 * AgroalDataSource.
 * The AgroalDataSource is created dynamically for each tenant based on its
 * configuration.
 */
@Alternative
@Priority(1)
@Singleton
@Transactional
@Default
@Unremovable
@PersistenceUnitExtension
public final class CustomTenantConnectionResolver implements TenantConnectionResolver {

    private static final String RESOLVED_DRIVER_CLASS = "org.postgresql.Driver";
    private static final String RESOLVED_DB_KIND = "postgresql";

    @Inject
    TransactionManager transactionManager;

    @Inject
    TransactionSynchronizationRegistry transactionSynchronizationRegistry;

    @Inject
    Instance<AgroalPoolInterceptor> agroalPoolInterceptors;

    @Inject
    TenantProperties configuration;

    @Inject
    TenantService tenantService;

    /**
     * Resolves the tenant's ConnectionProvider based on the tenantId.
     *
     * @param tenantId The tenantId for which to resolve the ConnectionProvider.
     * @return The ConnectionProvider for the specified tenant.
     */
    @Override
    public ConnectionProvider resolve(String tenantId) {
        Log.debugv("resolve({0})", tenantId);
        if (!Objects.equals(tenantId, "default")) {

            return new CustomConnectionProvider(this.doCreateDataSource(tenantId));

        }
        return new QuarkusConnectionProvider(Arc.container().instance(AgroalDataSource.class).get());
    }

    /**
     * Creates an AgroalDataSource dynamically for the given tenant based on its
     * configuration.
     *
     * @param tenant The tenantId for which to create the DataSource.
     * @return The AgroalDataSource for the specified tenant.
     */
    public AgroalDataSource doCreateDataSource(String tenant) {

        TenantDTO tenantConfig = TenantDataSource.get(tenant);

        if (tenantConfig == null) {

            // This will get new tenants and validate migrations
            // Will not close any connections that are already open
            // But be aware that it will spaw a lot of connections
            TenantAwareThread thread = new TenantAwareThread(() -> tenantService.updateAllTenants());
            thread.start();

            try {
                // Join doesn't have effect with Virtual Thread
                thread.join();
                tenantConfig = Optional.ofNullable(TenantDataSource.get(tenant))
                        .orElseThrow(() -> new WebServiceException("No config for this client"));

            } catch (InterruptedException e) {
                e.printStackTrace();

                throw new WebServiceException("Fail to get config for this client");
            }

        }

        if (tenantConfig == null || tenantConfig.getDatasourceName() == null) {

            throw new WebServiceException("No database URL defined");
        }

        Class<?> driver;
        try {
            driver = Class.forName(RESOLVED_DRIVER_CLASS, true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException ex) {

            Log.errorv(
                    "Unable to load the datasource driver " + RESOLVED_DRIVER_CLASS + " for datasource " + tenant);

            throw new WebServiceException("Unable to connect to database");
        }

        AgroalDataSourceConfiguration dataSourceConfiguration = this.createDatasourceConfiguration(
                tenantConfig, driver);

        AgroalDataSource dataSource = this.createAgroalDatasource(dataSourceConfiguration, tenant);

        Log.debugv("Started datasource {0} connected to {1}", tenant,
                dataSourceConfiguration.connectionPoolConfiguration().connectionFactoryConfiguration().jdbcUrl());

        Collection<AgroalPoolInterceptor> interceptorList = this.agroalPoolInterceptors
                .select(tenant != null && !DataSourceUtil.isDefault(tenant)
                        ? new io.quarkus.agroal.DataSource.DataSourceLiteral(tenant)
                        : Default.Literal.INSTANCE)
                .stream().collect(Collectors.toList());

        if (!interceptorList.isEmpty()) {
            dataSource.setPoolInterceptors(interceptorList);
        }

        FlyWayThread migrationThread = new FlyWayThread(tenantConfig);

        // Should await?
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            executor.submit(migrationThread);
        }

        return dataSource;
    }

    /**
     * Creates the AgroalDataSource using the given configuration.
     *
     * @param agroalConfiguration The AgroalDataSourceConfiguration.
     * @param tenant              The tenantId for the AgroalDataSource.
     * @return The created AgroalDataSource.
     */
    private AgroalDataSource createAgroalDatasource(AgroalDataSourceConfiguration agroalConfiguration,
            String tenant) {

        return new DataSource(agroalConfiguration, new AgroalEventLoggingListener(tenant));
    }

    /**
     * Creates the AgroalDataSourceConfigurationSupplier based on the tenant's
     * configuration.
     *
     * @param tenantConfig The tenant's configuration.
     * @param driver       The driver class to be used.
     * @return The AgroalDataSourceConfigurationSupplier.
     */

    private AgroalDataSourceConfiguration createDatasourceConfiguration(TenantDTO tenantConfig,
            Class<?> driver) {
        AgroalDataSourceConfigurationSupplier dataSourceConfiguration = new AgroalDataSourceConfigurationSupplier();
        dataSourceConfiguration.metricsEnabled(false);

        AgroalConnectionPoolConfigurationSupplier poolConfiguration = dataSourceConfiguration
                .connectionPoolConfiguration();

        io.agroal.api.transaction.TransactionIntegration txIntegration = new NarayanaTransactionIntegration(
                this.transactionManager, this.transactionSynchronizationRegistry);

        poolConfiguration.transactionIntegration(txIntegration);
        poolConfiguration.initialSize(configuration.getMinSize());
        poolConfiguration.minSize(configuration.getMinSize());
        poolConfiguration.maxSize(configuration.getMaxSize());
        poolConfiguration.maxLifetime(Duration.ofMinutes(30));

        poolConfiguration.connectionValidator(AgroalConnectionPoolConfiguration.ConnectionValidator.defaultValidator());
        poolConfiguration.acquisitionTimeout(Duration.ofSeconds(5));
        poolConfiguration.validationTimeout(Duration.ofSeconds(120));

        poolConfiguration.reapTimeout(Duration.ofSeconds(300));

        AgroalConnectionFactoryConfigurationSupplier connectionFactoryConfiguration = poolConfiguration
                .connectionFactoryConfiguration();

        connectionFactoryConfiguration.jdbcUrl(tenantConfig.getDatasourceHost() + tenantConfig.getDatasourceName());
        connectionFactoryConfiguration.connectionProviderClass(driver);
        connectionFactoryConfiguration.trackJdbcResources(true);
        connectionFactoryConfiguration.jdbcProperty("ApplicationName", configuration.getAplicationName());
        connectionFactoryConfiguration.principal(new NamePrincipal(tenantConfig.getDatasourceUsername()));
        connectionFactoryConfiguration.credential(new SimplePassword(tenantConfig.getDatasourcePassword()));

        InstanceHandle<AgroalConnectionConfigurer> agroalConnectionConfigurerHandle = Arc.container()
                .instance(AgroalConnectionConfigurer.class, new JdbcDriver.JdbcDriverLiteral(RESOLVED_DB_KIND));

        if (!tenantConfig.getFlagSSL() && agroalConnectionConfigurerHandle.isAvailable()) {
            agroalConnectionConfigurerHandle.get().disableSslSupport(RESOLVED_DB_KIND, dataSourceConfiguration);
        }

        return dataSourceConfiguration.get();
    }
}