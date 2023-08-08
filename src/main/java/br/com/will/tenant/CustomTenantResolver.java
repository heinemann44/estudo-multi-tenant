package br.com.will.tenant;

import java.util.Optional;

import br.com.will.constant.TenantConstant;
import io.quarkus.arc.Unremovable;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
@Unremovable
@PersistenceUnitExtension
public class CustomTenantResolver implements TenantResolver {

    @Inject
    RoutingContext context;

    @Override
    public String getDefaultTenantId() {
        return TenantConstant.DEFAULT;
    }

    @Override
    public String resolveTenantId() {

        String tenant;

        try {
            tenant = Optional.ofNullable(context.request().headers().get("x-tenant")).orElse(TenantConstant.DEFAULT);

        } catch (Exception e) {
            tenant = TenantConstant.DEFAULT;
        }

        TenantContext.setTenant(tenant);

        return tenant;

    }

}