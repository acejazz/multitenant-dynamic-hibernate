package com.tanio.multitenant;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class CurrentDataSources {
    private final ThreadLocal<DataSource> customersDatasource = new ThreadLocal<>();
    private final ThreadLocal<DataSource> inventoryDatasource = new ThreadLocal<>();

    public void set(DataSource customerDatasource, DataSource inventoryDatasource) {
        this.customersDatasource.set(customerDatasource);
        this.inventoryDatasource.set(inventoryDatasource);
    }

    public ThreadLocal<DataSource> getCustomersDatasource() {
        return customersDatasource;
    }

    public ThreadLocal<DataSource> getInventoryDatasource() {
        return inventoryDatasource;
    }

    public void reset() {
        customersDatasource.remove();
        inventoryDatasource.remove();
    }
}
