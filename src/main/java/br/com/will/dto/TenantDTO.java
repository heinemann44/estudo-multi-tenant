package br.com.will.dto;

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

        private Boolean flagSSL;

}