package br.com.will.thread;

import io.quarkus.logging.Log;

public class TenantAwareThread extends Thread {
    public TenantAwareThread(Runnable target) {
        super(target);
    }

    @Override
    public void run() {
        Log.infov("Running TenantAwareThread with {0}", Thread.currentThread());

        super.run();
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
