package br.com.will.repository;

import br.com.will.model.TenantConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TenantConfigRepository implements PanacheRepository<TenantConfig> {

    public TenantConfig findByTenantId(String tenant) {
        return find("tenantId", tenant).firstResult();
    }

}
