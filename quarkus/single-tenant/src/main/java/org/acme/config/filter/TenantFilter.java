package org.acme.config.filter;

import java.util.Objects;

import org.acme.config.database.TenantContext;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

import jakarta.ws.rs.container.ContainerRequestContext;

// @Provider
class TenantFilter {
    
    private static final String PRIVATE_TENANT_HEADER = "tenant-id";

    private static final String DEFAULT_TENANT = "public";

    private static final ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static String getCurrentTenant() {
        String tenant = currentTenant.get();
        return Objects.requireNonNullElse(tenant, DEFAULT_TENANT);
        // return "teste";
    }

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }
    
    public static void clear() {
        currentTenant.remove();
    }

    @ServerRequestFilter
    public void preMatchingFilter(ContainerRequestContext requestContext) {
        String privateTenant = requestContext.getHeaderString(PRIVATE_TENANT_HEADER);

        if (privateTenant != null) {
            TenantContext.setCurrentTenant(privateTenant);
        }
    }

    // @Override
    // public void filter(ContainerRequestContext requestContext){
    //     String privateTenant = requestContext.getHeaderString("tenant-id");
    //     // String privateTenant = requestContext.getHeaderString(PRIVATE_TENANT_HEADER);

    //     if (privateTenant != null) {
    //         TenantContext.setCurrentTenant(privateTenant);
    //     }
    // }

    
}