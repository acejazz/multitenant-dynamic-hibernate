package com.tanio.multitenant.customers;

import com.tanio.multitenant.CombinedDataSource;
import com.tanio.multitenant.DynamicDataSource;

import java.util.function.Supplier;

public class CustomersDynamicDatasource extends DynamicDataSource {
    public CustomersDynamicDatasource(Supplier<CombinedDataSource> dataSource) {
        super(dataSource, CombinedDataSource::customersDatasource);
    }
}