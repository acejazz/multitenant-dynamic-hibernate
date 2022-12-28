package com.tanio.multitenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

public class CustomersDynamicDatasource extends AbstractRoutingDataSource {
    private final ThreadLocal<CombinedDataSource> dataSource;

    public CustomersDynamicDatasource(ThreadLocal<CombinedDataSource> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        return dataSource.get().customersDatasource;
    }

    @Override
    public void afterPropertiesSet() {
    }
}