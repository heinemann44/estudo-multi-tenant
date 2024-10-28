package br.com.will.tenant;

import java.io.Serializable;

public class TenantContext implements Serializable {

    private static transient ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static String getTenant() {
        return currentTenant.get();
    }

    public static void setTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static void clear() {
        currentTenant.remove();
    }
}
