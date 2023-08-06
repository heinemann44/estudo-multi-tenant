package br.com.will.tenant;

public class TenantContext {
    private TenantContext() {
    }

    private static final ThreadLocal<String> tenantHolder = new ThreadLocal<>();

    public static String getTenant() {
        return tenantHolder.get();
    }

    public static void setTenant(String tenant) {
        tenantHolder.set(tenant);
    }

    public static void clearTenant() {
        tenantHolder.remove();
    }
}
