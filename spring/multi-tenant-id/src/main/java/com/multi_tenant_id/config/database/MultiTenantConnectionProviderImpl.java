package com.multi_tenant_id.config.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider<String> {

    private static final String DEFAULT_TENANT_ID = "public";

    private Map<String, DataSource> map = new HashMap<>();

    public MultiTenantConnectionProviderImpl(DataSource dataSource) {
        map.put(DEFAULT_TENANT_ID, dataSource);
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return map.get(DEFAULT_TENANT_ID).getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        return  map.get(DEFAULT_TENANT_ID).getConnection();
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        connection.setSchema(DEFAULT_TENANT_ID);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean isUnwrappableAs(Class aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}