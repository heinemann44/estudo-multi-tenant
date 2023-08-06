package br.com.will.thread;

import java.util.concurrent.Callable;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import br.com.will.dto.TenantDTO;
import br.com.will.tenant.DatasourceTenantConfigResolver;
import io.agroal.api.AgroalDataSource;
import io.quarkus.logging.Log;
import jakarta.enterprise.inject.spi.CDI;

public class FlyWayThread implements Callable<Boolean> {

    private final FluentConfiguration flywayConfiguration;
    private final TenantDTO clientConfig;

    public FlyWayThread(FluentConfiguration flywayConfiguration, TenantDTO clientConfig) {
        this.flywayConfiguration = flywayConfiguration;
        this.clientConfig = clientConfig;
    }

    @Override
    public Boolean call() throws Exception {

        DatasourceTenantConfigResolver resolver = CDI.current().select(DatasourceTenantConfigResolver.class).get();

        try (AgroalDataSource dataSource = resolver
                .doCreateDataSource(clientConfig.getTenantId())) {

            flywayConfiguration.dataSource(dataSource);
            Flyway f = new Flyway(flywayConfiguration);
            f.migrate();

        } catch (Exception e) {
            Log.errorv("Error when running migration for: {0}", clientConfig.getTenantId());

        }
        return Boolean.TRUE;
    }

}
