package br.com.will.thread;

import br.com.will.tenant.TenantContext;
import io.quarkus.logging.Log;

public class TenantAwareThread extends Thread {

    private final String tenant;

    public TenantAwareThread(Runnable target) {
        super(target);
        tenant = "default";
    }

    public TenantAwareThread(String tenant, Runnable target) {
        super(target);
        this.tenant = tenant;
    }

    @Override
    public void run() {
        Log.infov("Running TenantAwareThread with {0}", Thread.currentThread());
        TenantContext.setTenant(tenant);

        super.run();

        TenantContext.clear();
    }

    // Override the start method to use VirtualThread
    /*
     * @Override
     * public void start() {
     * Thread virtualThread = Thread.startVirtualThread(this::run);
     * virtualThread.setName(this.getName());
     * }
     */
}
