package com.multi_tenant_id.config.filter;

import java.io.IOException;
import java.util.Objects;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.multi_tenant_id.config.database.TenantContext;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(1)
class TenantFilter implements Filter {

    public static final String PRIVATE_TENANT_HEADER = "tenant-id";
    private static final String DEFAULT_TENANT = "public";
    private static final ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static String getCurrentTenant() {
        String tenant = currentTenant.get();
        return Objects.requireNonNullElse(tenant, DEFAULT_TENANT);
    }
    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }
    public static void clear() {
        currentTenant.remove();
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String privateTenant = req.getHeader(PRIVATE_TENANT_HEADER);

        if (privateTenant != null) {
            TenantContext.setCurrentTenant(privateTenant);
        }

        chain.doFilter(request, response);
    }
}