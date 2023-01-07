package com.tanio.multitenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

public class DynamicDatasource extends AbstractRoutingDataSource {
    private final CurrentTenantKey currentTenantKey;
    private final DataSourceProvider dataSourceProvider;

    public DynamicDatasource(CurrentTenantKey currentTenantKey, DataSourceProvider dataSourceProvider) {
        this.currentTenantKey = currentTenantKey;
        this.dataSourceProvider = dataSourceProvider;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        return dataSourceProvider.getForTenantKey(currentTenantKey.get());
    }

    @Override
    public void afterPropertiesSet() {
    }
}