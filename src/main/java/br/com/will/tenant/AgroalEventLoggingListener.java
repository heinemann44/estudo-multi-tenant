package br.com.will.tenant;

import java.sql.Connection;

import br.com.will.dto.TenantConfigsDTO;
import io.agroal.api.AgroalDataSourceListener;
import io.quarkus.logging.Log;

final class AgroalEventLoggingListener implements AgroalDataSourceListener {
    /**
     *
     */
    private final String tenant;

    public AgroalEventLoggingListener(String name) {
        this.tenant = name;

    }

    @Override
    public void onConnectionInvalid(Connection connection) {
        Log.warnv("Connections is not valid for tenant: {0}", tenant);
        TenantConfigsDTO.remove(tenant);
    }

    @Override
    public void onConnectionCreation(Connection connection) {
        Log.warnv("Connection for tenant created: {0}", tenant);
    }

    @Override
    public void onConnectionDestroy(Connection connection) {
        Log.warnv("Connection for tenant destroyed: {0}", tenant);
    }

    @Override
    public void onWarning(Throwable throwable) {
        TenantConfigsDTO.remove(tenant);
    }
}
