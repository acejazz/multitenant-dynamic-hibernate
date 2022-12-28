package com.tanio.multitenant.inventory;

import com.tanio.multitenant.CombinedDataSource;
import com.tanio.multitenant.DynamicDataSource;

import java.util.function.Supplier;

public class InventoryDynamicDatasource extends DynamicDataSource {
    public InventoryDynamicDatasource(Supplier<CombinedDataSource> dataSource) {
        super(dataSource, CombinedDataSource::inventoryDatasource);
    }
}