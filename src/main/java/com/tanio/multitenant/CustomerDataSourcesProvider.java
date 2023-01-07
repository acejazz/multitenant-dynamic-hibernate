package com.tanio.multitenant;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;

@Component
public class CustomerDataSourcesProvider implements DataSourceProvider {
    private final HashMap<String, DataSource> datasources = new HashMap<>();

    public DataSource getForTenantKey(String tenantKey) {
        return datasources.get(tenantKey);
    }

    public void addDataSource(DataSource dataSource, String tenantKey) {
        datasources.put(tenantKey, dataSource);
    }
}