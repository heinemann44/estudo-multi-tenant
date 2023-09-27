package br.com.will.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import br.com.will.dto.TenantDTO;
import br.com.will.interceptor.WebServiceException;
import br.com.will.model.TenantConfig;
import br.com.will.repository.TenantConfigRepository;
import br.com.will.tenant.CustomConnectionProvider;
import br.com.will.tenant.TenantDataSource;
import br.com.will.util.ObjectMapperUtils;
import io.quarkus.logging.Log;
import io.quarkus.runtime.util.StringUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class TenantService {

    @Inject
    TenantConfigRepository tenantConfigRepository;

    @Inject
    SessionFactory sessionFactory;

    @Inject
    CustomConnectionProvider customConnectionProvider;

    @ActivateRequestContext
    public void updateTenant(String tenant) {
        Log.info("Updating tenants");

        TenantConfig newTenant = tenantConfigRepository.findByTenantId(tenant);

        if (newTenant != null) {

            TenantDataSource.add(tenant, ObjectMapperUtils.map(newTenant, TenantDTO.class));
        }

    }

    @Transactional
    public void delete(Long id) {
        Log.info("Running tenant delete");

        TenantConfig tenant = tenantConfigRepository.findById(id);

        if (tenant != null) {

            customConnectionProvider.removeDatasource(tenant.getTenantId());

            tenantConfigRepository.deleteById(id);
        }
    }

    public List<TenantDTO> listAll() {
        Log.info("Running tenant listAll");
        return ObjectMapperUtils.mapAll(tenantConfigRepository.findAll().list(), TenantDTO.class);
    }

    @Transactional
    public void save(TenantDTO tenantDTO) {
        Log.info("Running tenant save");

        if (tenantDTO == null) {
            throw new WebServiceException("You must inform some value");
        }

        TenantConfig tenantConfig;
        if (tenantDTO.getId() != null) {
            tenantConfig = tenantConfigRepository.findById(tenantDTO.getId());

            if (tenantConfig == null) {
                throw new WebServiceException("Tenant not found", Status.NOT_FOUND);
            }

            customConnectionProvider.removeDatasource(tenantConfig.getTenantId());
        } else {
            tenantConfig = new TenantConfig();
        }

        if (StringUtil.isNullOrEmpty(tenantDTO.getTenantId()) || StringUtil.isNullOrEmpty(tenantDTO.getDatasourceHost())
                || StringUtil.isNullOrEmpty(tenantDTO.getDatasourceName())
                || StringUtil.isNullOrEmpty(tenantDTO.getDatasourcePassword())
                || StringUtil.isNullOrEmpty(tenantDTO.getDatasourceUsername())) {

            throw new WebServiceException("Must inform all values");
        }

        tenantConfig.setDatasourceHost(tenantDTO.getDatasourceHost());
        tenantConfig.setDatasourceName(tenantDTO.getDatasourceName());
        tenantConfig.setDatasourcePassword(tenantDTO.getDatasourcePassword());
        tenantConfig.setDatasourceUsername(tenantDTO.getDatasourceUsername());
        tenantConfig.setFlagSSL(Optional.ofNullable(tenantDTO.getFlagSSL()).orElse(Boolean.FALSE));
        tenantConfig.setTenantId(tenantDTO.getTenantId());

        tenantConfigRepository.persist(tenantConfig);
    }

}
