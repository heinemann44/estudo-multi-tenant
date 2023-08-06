package br.com.will.tenant;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import br.com.will.dto.TenantHolderDTO;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Produces;

public class TenantConfiguration {

    @RequestScoped
    @Produces
    public TenantHolderDTO tenantHolder(RoutingContext context) {

        String tenantId = context.request().headers().get("x-tenant");

        if (StringUtils.isBlank(tenantId)) {

            tenantId = TenantContext.getTenant();
        }

        return new TenantHolderDTO(Optional.ofNullable(tenantId).orElse("default"));

    }

}
