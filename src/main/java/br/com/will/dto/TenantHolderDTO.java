package br.com.will.dto;

import java.io.Serializable;

public class TenantHolderDTO implements Serializable {

    private String tenant;

    public TenantHolderDTO() {
    }

    public TenantHolderDTO(String tenant) {
        this.tenant = tenant;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
