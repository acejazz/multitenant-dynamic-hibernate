package com.tanio.multitenant.customers;

import com.tanio.multitenant.CombinedDataSource;
import com.tanio.multitenant.DynamicDataSource;

public class CustomersDynamicDatasource extends DynamicDataSource {
    public CustomersDynamicDatasource(ThreadLocal<CombinedDataSource> dataSource) {
        super(dataSource, CombinedDataSource::customersDatasource);
    }
}