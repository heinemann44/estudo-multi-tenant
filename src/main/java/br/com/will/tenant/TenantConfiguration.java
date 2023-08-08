package br.com.will.tenant;

import java.util.Optional;

import br.com.will.dto.TenantHolderDTO;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Produces;

public class TenantConfiguration {

    @RequestScoped
    @Produces
    public TenantHolderDTO tenantHolder(RoutingContext context) {

        return new TenantHolderDTO(Optional.ofNullable(context.request().headers().get("x-tenant")).orElse("default"));

    }

}
