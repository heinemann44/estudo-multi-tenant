package br.com.will.thread;

import br.com.will.constant.TenantConstant;
import br.com.will.tenant.TenantContext;

public class TenantAwareThread extends Thread {
    private String tenant = null;

    public TenantAwareThread(Runnable target) {
        super(target);
        this.tenant = TenantConstant.DEFAULT;
    }

    public TenantAwareThread(Runnable target, String tenant) {
        super(target);
        this.tenant = tenant;
    }

    @Override
    public void run() {
        TenantContext.setTenant(tenant);

        super.run();
        TenantContext.clearTenant();
    }
}
