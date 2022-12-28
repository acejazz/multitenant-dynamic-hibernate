package com.tanio.multitenant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourcesConfiguration {

    @Bean(name = "customersDataSource")
    DataSource customersDataSource(CurrentDataSources currentDataSources) {
        return new DynamicDatasource(currentDataSources.getCustomersDatasource());
    }

    @Bean(name = "inventoryDatasource")
    DataSource inventoryDataSource(CurrentDataSources currentDataSources) {
        return new DynamicDatasource(currentDataSources.getInventoryDatasource());
    }
}