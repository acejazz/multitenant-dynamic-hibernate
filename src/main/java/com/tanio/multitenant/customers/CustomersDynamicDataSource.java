package com.tanio.multitenant.customers;

import com.tanio.multitenant.CombinedDataSource;
import com.tanio.multitenant.DynamicDataSource;

import java.util.function.Supplier;

public class CustomersDynamicDataSource extends DynamicDataSource {
    public CustomersDynamicDataSource(Supplier<CombinedDataSource> dataSource) {
        super(dataSource, CombinedDataSource::customersDatasource);
    }
}