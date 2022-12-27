package com.tanio.multitenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class MultiTenantDatasource extends AbstractRoutingDataSource {
    private final CurrentTenantKey currentTenantKey;
    private final Map<String, DataSource> dataSources;

    public MultiTenantDatasource(CurrentTenantKey currentTenantKey, Map<String, DataSource> dataSources) {
        this.currentTenantKey = currentTenantKey;
        this.dataSources = dataSources;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        return dataSources.get(currentTenantKey.get());
    }

    @Override
    public void afterPropertiesSet() {
    }
}
