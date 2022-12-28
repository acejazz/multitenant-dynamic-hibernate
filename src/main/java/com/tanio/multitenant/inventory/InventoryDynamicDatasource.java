package com.tanio.multitenant.inventory;

import com.tanio.multitenant.CombinedDataSource;
import com.tanio.multitenant.DynamicDataSource;

public class InventoryDynamicDatasource extends DynamicDataSource {
    public InventoryDynamicDatasource(ThreadLocal<CombinedDataSource> dataSource) {
        super(dataSource, CombinedDataSource::inventoryDatasource);
    }
}