package com.tanio.multitenant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourcesConfiguration {

    @Bean(name = "customersDataSource")
    DataSource customersDataSource(CurrentTenantKey currentTenantKey,
                                   CustomerDataSourcesProvider customerDataSourcesProvider) {
        return new DynamicDatasource(currentTenantKey, customerDataSourcesProvider);
    }

    @Bean(name = "inventoryDatasource")
    DataSource inventoryDataSource(CurrentTenantKey currentTenantKey,
                                   InventoryDataSourcesProvider inventoryDataSourcesProvider) {
        return new DynamicDatasource(currentTenantKey, inventoryDataSourcesProvider);
    }
}