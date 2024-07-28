package config.database;

import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;
import jakarta.enterprise.context.RequestScoped;

@PersistenceUnitExtension 
@RequestScoped 
public class CustomTenantResolver implements TenantResolver {

    @Override
    public String getDefaultTenantId() {
        return "base";
    }

    @Override
    public String resolveTenantId() {
        return TenantContext.getCurrentTenant();
    }

}