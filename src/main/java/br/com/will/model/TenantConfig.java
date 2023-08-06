package br.com.will.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_tenant_config")
public class TenantConfig {

    @Id
    @SequenceGenerator(name = "gen_tenant_config", sequenceName = "gen_tenant_config", initialValue = 3, allocationSize = 1)
    @GeneratedValue(generator = "gen_tenant_config")
    private Long id;

    @Column(name = "tx_tenant_id", nullable = false)
    private String tenantId;

    @Column(nullable = false, name = "tx_datasource_host")
    private String datasourceHost;

    @Column(name = "tx_datasource_name", nullable = false)
    private String datasourceName;

    // Should encript before save
    @Column(name = "tx_datasource_username", nullable = false)
    private String datasourceUsername;

    // Should encript before save
    @Column(name = "tx_datasource_password", nullable = false)
    private String datasourcePassword;

    @Column(name = "fl_ssl", nullable = false)
    private Boolean flagSSL;

    @Override
    public String toString() {
        return "TenantConfig [id=" + id + ", tenantId=" + tenantId + ", datasourceHost=" + datasourceHost
                + ", datasourceName=" + datasourceName + ", datasourceUsername=" + datasourceUsername
                + ", datasourcePassword=" + datasourcePassword + ", flagSSL=" + flagSSL + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TenantConfig other = (TenantConfig) obj;

        if (tenantId == null) {
            if (other.tenantId != null)
                return false;
        } else if (!tenantId.equals(other.tenantId))
            return false;
        if (datasourceHost == null) {
            if (other.datasourceHost != null)
                return false;
        } else if (!datasourceHost.equals(other.datasourceHost))
            return false;
        if (datasourceName == null) {
            if (other.datasourceName != null)
                return false;
        } else if (!datasourceName.equals(other.datasourceName))
            return false;
        if (datasourceUsername == null) {
            if (other.datasourceUsername != null)
                return false;
        } else if (!datasourceUsername.equals(other.datasourceUsername))
            return false;
        if (datasourcePassword == null) {
            if (other.datasourcePassword != null)
                return false;
        } else if (!datasourcePassword.equals(other.datasourcePassword))
            return false;
        if (flagSSL == null) {
            if (other.flagSSL != null)
                return false;
        } else if (!flagSSL.equals(other.flagSSL))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
        result = prime * result + ((datasourceHost == null) ? 0 : datasourceHost.hashCode());
        result = prime * result + ((datasourceName == null) ? 0 : datasourceName.hashCode());
        result = prime * result + ((datasourceUsername == null) ? 0 : datasourceUsername.hashCode());
        result = prime * result + ((datasourcePassword == null) ? 0 : datasourcePassword.hashCode());
        result = prime * result + ((flagSSL == null) ? 0 : flagSSL.hashCode());
        return result;
    }

}
