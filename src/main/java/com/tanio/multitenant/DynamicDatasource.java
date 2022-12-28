package com.tanio.multitenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

public class DynamicDatasource extends AbstractRoutingDataSource {
    private final ThreadLocal<DataSource> dataSource;

    public DynamicDatasource(ThreadLocal<DataSource> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        return dataSource.get();
    }

    @Override
    public void afterPropertiesSet() {
    }
}