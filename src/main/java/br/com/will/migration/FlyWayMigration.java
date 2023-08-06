package br.com.will.migration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import br.com.will.dto.TenantConfigsDTO;
import br.com.will.dto.TenantDTO;
import br.com.will.tenant.DatasourceTenantConfigResolver;
import br.com.will.thread.FlyWayThread;
import io.agroal.api.AgroalDataSource;
import io.quarkus.arc.Unremovable;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * FlyWay doesnt work with GraalNative
 * https://github.com/flyway/flyway/issues/2927
 */
@Singleton
@Unremovable
public class FlyWayMigration {

    @Inject
    DatasourceTenantConfigResolver datasourceTenantConfigResolver;

    public void runMigrationFor(String tenantId) {
        Optional<TenantDTO> tenant = TenantConfigsDTO.findOptionalClient(tenantId);

        if (tenant.isPresent()) {
            this.execMigration(this.getFlywayConfiguration(), tenant.get());
        }
    }

    public void checkMigration() {

        FluentConfiguration flywayConfiguration = this.getFlywayConfiguration();

        Set<TenantDTO> tenants = TenantConfigsDTO.getConfigs();

        ExecutorService executorService = Executors.newFixedThreadPool(tenants.size());

        List<FlyWayThread> threads = new ArrayList<>();

        tenants.forEach(clientConfig -> threads.add(new FlyWayThread(flywayConfiguration, clientConfig)));

        try {
            executorService.invokeAll(threads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.info("Migration has been finalized");

    }

    private void execMigration(FluentConfiguration flywayConfiguration, TenantDTO clientConfig) {
        Log.info("Running migration for: " + clientConfig.getTenantId());

        try (AgroalDataSource dataSource = datasourceTenantConfigResolver
                .doCreateDataSource(clientConfig.getTenantId())) {

            flywayConfiguration.dataSource(dataSource);
            Flyway f = new Flyway(flywayConfiguration);
            f.migrate();

        } catch (Exception e) {
            Log.errorv("Error where running migration for: {0}", clientConfig.getTenantId());

        }
    }

    private FluentConfiguration getFlywayConfiguration() {
        FluentConfiguration flywayConfiguration = Flyway.configure();
        flywayConfiguration.table("tb_schemamigration");
        flywayConfiguration.locations("db/migration/client");
        flywayConfiguration.sqlMigrationPrefix("v");
        flywayConfiguration.baselineOnMigrate(Boolean.TRUE);
        flywayConfiguration.connectRetries(0);
        flywayConfiguration.encoding(StandardCharsets.UTF_8);
        flywayConfiguration.validateOnMigrate(Boolean.FALSE);
        return flywayConfiguration;
    }

}
