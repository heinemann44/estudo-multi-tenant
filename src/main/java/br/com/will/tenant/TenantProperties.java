package br.com.will.tenant;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TenantProperties {

    @ConfigProperty(name = "custom.datasource.max-size", defaultValue = "200")
    private Integer maxSize;

    @ConfigProperty(name = "custom.datasource.min-size", defaultValue = "15")
    private Integer minSize;

    @ConfigProperty(name = "quarkus.application.name", defaultValue = "Quarkus Multitenacy")
    private String aplicationName;

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
