package br.com.will.tenant;

import br.com.will.constant.TenantConstant;
import br.com.will.dto.TenantHolderDTO;
import io.quarkus.arc.Unremovable;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
@Unremovable
@PersistenceUnitExtension
public class CustomTenantResolver implements TenantResolver {

    @Inject
    TenantHolderDTO tenantHolder;

    @Override
    public String getDefaultTenantId() {
        return TenantConstant.DEFAULT;
    }

    @Override
    public String resolveTenantId() {
        try {
            return tenantHolder.getTenant();
        } catch (Exception e) {
            return TenantConstant.DEFAULT;
        }
    }

}