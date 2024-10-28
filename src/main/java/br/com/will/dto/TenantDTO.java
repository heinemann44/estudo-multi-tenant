package br.com.will.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.agroal.api.AgroalDataSource;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class TenantDTO {

        private Long id;

        private String tenantId;

        private String datasourceHost;

        private String datasourceName;

        private String datasourceScheme;

        private String datasourceUsername;

        private String datasourcePassword;

        private Boolean flagSSL = Boolean.FALSE;

        @JsonIgnore
        private AgroalDataSource datasource;

}
