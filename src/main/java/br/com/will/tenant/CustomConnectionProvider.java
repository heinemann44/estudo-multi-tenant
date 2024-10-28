package br.com.will.tenant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;

import br.com.will.constant.TenantConstant;
import br.com.will.dto.TenantDTO;
import br.com.will.interceptor.WebServiceException;
import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import io.quarkus.arc.Arc;
import io.quarkus.logging.Log;
import io.quarkus.runtime.util.StringUtil;
import jakarta.inject.Singleton;

@Singleton
public final class CustomConnectionProvider implements ConnectionProvider {

    public CustomConnectionProvider(final AgroalDataSource dataSource) {

        String tenant = TenantContext.getTenant();
        if (StringUtil.isNullOrEmpty(tenant)) {
            tenant = TenantConstant.DEFAULT;
        }

        TenantDTO tenantConfig = TenantDataSource.get(tenant);
        if (tenantConfig != null) {

            tenantConfig.setDatasource(dataSource);
            TenantDataSource.add(tenant, tenantConfig);
        }

    }

    @Override
    public Connection getConnection() throws SQLException {
        // Log.info("CustomConnectionProvider getConnection");

        String tenant = Optional.ofNullable(TenantContext.getTenant()).orElse(TenantConstant.DEFAULT);

        TenantDTO tenantConfig = TenantDataSource.get(tenant);

        if (tenantConfig == null || tenantConfig.getDatasource() == null) {

            CustomTenantConnectionResolver connectionResolver = Arc.container()
                    .instance(CustomTenantConnectionResolver.class).get();

            connectionResolver.resolve(tenant);

            tenantConfig = Optional.ofNullable(TenantDataSource.get(tenant))
                    .orElseThrow(() -> new WebServiceException("Tenant not found"));
        }

        return tenantConfig.getDatasource().getConnection();
    }

    public void removeDatasource(String tenantId) {
        Log.info("CustomConnectionProvider removeDatasource");

        TenantDTO tenantConfig = TenantDataSource.get(tenantId);
        if (tenantConfig != null && tenantConfig.getDatasource() != null) {

            tenantConfig.getDatasource().close();
            TenantDataSource.remove(tenantId);
        }
    }

    @Override
    public void closeConnection(final Connection connection) throws SQLException {
        // Log.info("CustomConnectionProvider closeConnection");
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

    @Override
    public boolean isUnwrappableAs(final Class<?> unwrapType) {
        return ConnectionProvider.class.equals(unwrapType)
                || CustomConnectionProvider.class.isAssignableFrom(unwrapType)
                || DataSource.class.isAssignableFrom(unwrapType) || AgroalDataSource.class.isAssignableFrom(unwrapType);
    }

    @Override
    public <T> T unwrap(final Class<T> unwrapType) {
        if (!ConnectionProvider.class.equals(unwrapType)
                && !CustomConnectionProvider.class.isAssignableFrom(unwrapType)) {

            if (!DataSource.class.isAssignableFrom(unwrapType)
                    && !AgroalDataSource.class.isAssignableFrom(unwrapType)) {
                throw new UnknownUnwrapTypeException(unwrapType);
            } else {
                return (T) TenantDataSource.get(TenantContext.getTenant()).getDatasource();
            }
        } else {
            return (T) this;
        }
    }

}
