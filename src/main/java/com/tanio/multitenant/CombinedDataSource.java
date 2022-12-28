package com.tanio.multitenant;

import javax.sql.DataSource;

public class CombinedDataSource {
    DataSource customersDatasource;
    DataSource inventoryDatasource;

    public CombinedDataSource(DataSource customersDatasource, DataSource inventoryDatasource) {
        this.customersDatasource = customersDatasource;
        this.inventoryDatasource = inventoryDatasource;
    }

    public DataSource getCustomersDatasource() {
        return customersDatasource;
    }

    public DataSource getInventoryDatasource() {
        return inventoryDatasource;
    }
}
