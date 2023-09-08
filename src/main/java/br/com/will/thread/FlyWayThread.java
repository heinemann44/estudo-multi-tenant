package br.com.will.thread;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import br.com.will.dto.TenantDTO;
import io.quarkus.logging.Log;

public class FlyWayThread implements Callable<Boolean> {

    private final TenantDTO clientConfig;

    public FlyWayThread(TenantDTO clientConfig) {
        this.clientConfig = clientConfig;
    }

    @Override
    public Boolean call() throws Exception {
        Log.infov("Running migration with {0}", Thread.currentThread());

        try {

            Log.infov("Running migration for: {0}", clientConfig.getTenantId());

            FluentConfiguration flywayConfiguration = this.getFlywayConfiguration();

            flywayConfiguration.dataSource(clientConfig.getDatasource());
            Flyway f = new Flyway(flywayConfiguration);
            f.migrate();

            Log.infov("Migration {0} has been finalized", clientConfig.getTenantId());

        } catch (Exception e) {
            e.printStackTrace();
            Log.errorv("Error when running migration for: {0}", clientConfig.getTenantId());

        }
        return Boolean.TRUE;
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
