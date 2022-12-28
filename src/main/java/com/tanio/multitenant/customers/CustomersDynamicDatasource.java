package com.tanio.multitenant.customers;

import com.tanio.multitenant.CombinedDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

public class CustomersDynamicDatasource extends AbstractRoutingDataSource {
    private final ThreadLocal<CombinedDataSource> dataSource;

    public CustomersDynamicDatasource(ThreadLocal<CombinedDataSource> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object determineCurrentLookupKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DataSource determineTargetDataSource() {
        return dataSource.get().customersDatasource();
    }

    @Override
    public void afterPropertiesSet() {
    }
}