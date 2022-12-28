package com.tanio.multitenant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourcesConfiguration {

    @Bean(name = "customersDataSource")
    DataSource customersDataSource(CurrentCombinedDatasource currentDataSources) {
        return new CustomersDynamicDatasource(currentDataSources.get());
    }

    @Bean(name = "inventoryDatasource")
    DataSource inventoryDataSource(CurrentCombinedDatasource currentDataSources) {
        return new InventoryDynamicDatasource(currentDataSources.get());
    }
}