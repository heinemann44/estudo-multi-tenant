package br.com.will.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.agroal.api.AgroalDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDTO {

        private Long id;

        private String tenantId;

        private String datasourceHost;

        private String datasourceName;

        private String datasourceUsername;

        private String datasourcePassword;

        private Boolean flagSSL = Boolean.FALSE;

        @JsonIgnore
        private AgroalDataSource datasource;

}
