package br.com.will.dto;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class TenantConfigsDTO {

    private TenantConfigsDTO() {

    }

    private static LinkedHashSet<TenantDTO> configs = new LinkedHashSet<>();

    public static Set<TenantDTO> getConfigs() {
        return configs;
    }

    public static void setConfigs(List<TenantDTO> tenants) {
        configs.clear();
        configs.addAll(tenants);
    }

    public static TenantDTO findClient(String tenant) {
        return configs.stream().filter(config -> config.getTenantId().equals(tenant)).findFirst().orElse(null);
    }

    public static Optional<TenantDTO> findOptionalClient(String tenant) {
        return configs.stream().filter(config -> config.getTenantId().equals(tenant)).findFirst();
    }

    public static void addClient(TenantDTO config) {
        configs.add(config);
    }

    public static void addClient(List<TenantDTO> config) {
        configs.addAll(config);
    }

    public static void remove(String tenant) {
        configs.removeIf(config -> config.getTenantId().equals(tenant));
    }
}
