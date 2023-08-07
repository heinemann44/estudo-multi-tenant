package br.com.will.startup;

import br.com.will.service.TenantService;
import io.quarkus.logging.Log;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class AppLifecycle {

    @Inject
    TenantService tenantService;

    void onStart(@Observes StartupEvent ev) {
        Log.info("Starting server...");
        tenantService.updateTenants();

    }

    void onStop(@Observes ShutdownEvent ev) {
        Log.info("The application is stopping...");
    }

}
