package br.com.will.startup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.will.service.TenantService;
import br.com.will.thread.MigrationThread;
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

        // Run the migrations without blocking server start
        MigrationThread migrationThread = new MigrationThread();

        // Don't use try-with-resources
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(migrationThread);

        Log.info("Server has been started");

    }

    void onStop(@Observes ShutdownEvent ev) {
        Log.info("The application is stopping...");
    }

}
