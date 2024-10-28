package br.com.will.tenant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import br.com.will.dto.TenantDTO;

public final class TenantDataSource {

    private TenantDataSource() {
    }

    private static final Map<String, TenantDTO> dataSourceMap = new ConcurrentHashMap<>();

    public static void add(String tenantId, TenantDTO tenantDTO) {
        dataSourceMap.put(tenantId, tenantDTO);
    }

    public static TenantDTO get(String tenantId) {
        return dataSourceMap.get(tenantId);
    }

    public static void remove(String tenantId) {
        dataSourceMap.remove(tenantId);
    }

    public static void listAll() {
        dataSourceMap.keySet().forEach((data) -> System.out.println(dataSourceMap.get(data)));
    }

}
