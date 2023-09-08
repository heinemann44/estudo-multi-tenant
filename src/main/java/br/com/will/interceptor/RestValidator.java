package br.com.will.interceptor;

import java.lang.reflect.Method;

import org.jboss.resteasy.reactive.server.ServerRequestFilter;

import io.quarkus.runtime.util.StringUtil;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RestValidator {

    @Context
    HttpHeaders httpHeaders;

    @Context
    ResourceInfo resourceInfo;

    private static final Uni<Response> FORBIDDEN = Uni.createFrom()
            .item(Response.status(Response.Status.FORBIDDEN).build());

    @ServerRequestFilter()
    public Uni<Response> filter() {

        Method method = resourceInfo.getResourceMethod();

        if (!method.isAnnotationPresent(PermitAll.class)) {

            final String tenant = httpHeaders.getHeaderString("x-tenant");

            if (StringUtil.isNullOrEmpty(tenant)) {
                return FORBIDDEN;
            }

        }

        return Uni.createFrom().nullItem();

    }

}
