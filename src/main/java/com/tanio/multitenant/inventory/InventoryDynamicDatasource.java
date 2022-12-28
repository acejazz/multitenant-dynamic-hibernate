package com.tanio.multitenant.inventory;

import com.tanio.multitenant.CombinedDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

public class InventoryDynamicDatasource extends AbstractRoutingDataSource {
    private final ThreadLocal<CombinedDataSource> dataSource;

    public InventoryDynamicDatasource(ThreadLocal<CombinedDataSource> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        return dataSource.get().inventoryDatasource();
    }

    @Override
    public void afterPropertiesSet() {
    }
}