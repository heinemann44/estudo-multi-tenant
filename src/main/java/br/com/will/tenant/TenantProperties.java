package br.com.will.tenant;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TenantProperties {

    @ConfigProperty(name = "custom.datasource.host")
    private String datasourceHost;

    @ConfigProperty(name = "custom.datasource.database-name")
    private String datasourceDatabaseName;

    @ConfigProperty(name = "custom.datasource.max-size", defaultValue = "20")
    private Integer maxSize;

    @ConfigProperty(name = "custom.datasource.min-size", defaultValue = "4")
    private Integer minSize;

    @ConfigProperty(name = "quarkus.application.name", defaultValue = "Quarkus Multitenacy")
    private String aplicationName;

    public String getDatasourceHost() {
        return datasourceHost;
    }

    public String getDatasourceDatabaseName() {
        return datasourceDatabaseName;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public Integer getMinSize() {
        return minSize;
    }

    public String getAplicationName() {
        return aplicationName;
    }
}
