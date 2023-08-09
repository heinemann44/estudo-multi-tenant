package br.com.will.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;

import br.com.will.dto.TenantDTO;
import br.com.will.interceptor.WebServiceException;
import br.com.will.model.TenantConfig;
import br.com.will.repository.TenantConfigRepository;
import br.com.will.tenant.CustomConnectionProvider;
import br.com.will.tenant.TenantContext;
import br.com.will.tenant.TenantDataSource;
import br.com.will.util.ObjectMapperUtils;
import io.quarkus.logging.Log;
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

    @Transactional
    public void updateTenants() {
        Log.info("Updating tenants");
        List<TenantConfig> newTenants = tenantConfigRepository.findAll().list();
        for (TenantConfig tenant : newTenants) {
            TenantDataSource.add(tenant.getTenantId(), ObjectMapperUtils.map(tenant, TenantDTO.class));
        }

    }

    @ActivateRequestContext
    public void updateAllTenants() {
        Log.info("Updating tenants");

        sessionFactory.withOptions().tenantIdentifier(TenantContext.getTenant()).openSession();

        List<TenantConfig> newTenants = tenantConfigRepository.findAll().list();

        for (TenantConfig tenant : newTenants) {
            TenantDataSource.add(tenant.getTenantId(), ObjectMapperUtils.map(tenant, TenantDTO.class));
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

        if (StringUtils.isBlank(tenantDTO.getTenantId()) || StringUtils.isBlank(tenantDTO.getDatasourceHost())
                || StringUtils.isBlank(tenantDTO.getDatasourceName())
                || StringUtils.isBlank(tenantDTO.getDatasourcePassword())
                || StringUtils.isBlank(tenantDTO.getDatasourceUsername())) {

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
