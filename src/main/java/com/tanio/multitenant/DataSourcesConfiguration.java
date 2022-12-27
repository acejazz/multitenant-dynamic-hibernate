package com.tanio.multitenant;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourcesConfiguration {

    @Bean(name = "customersDataSourcesMap")
    Map<String, DataSource> customersDataSourcesMap() {
        return new HashMap<>();
    }

    @Bean(name = "customersDataSource")
    DataSource customersDataSource(CurrentTenantKey currentTenantKey,
                                   @Qualifier("customersDataSourcesMap") Map<String, DataSource> customersDataSourcesMap) {
        return new MultiTenantDatasource(currentTenantKey, customersDataSourcesMap);
    }

    @Bean(name = "inventoryDataSourcesMap")
    Map<String, DataSource> inventoryDataSourcesMap() {
        return new HashMap<>();
    }

    @Bean(name = "inventoryDatasource")
    DataSource inventoryDataSource(CurrentTenantKey currentTenantKey,
                                   @Qualifier("inventoryDataSourcesMap") Map<String, DataSource> inventoryDataSourcesMap) {

        return new MultiTenantDatasource(currentTenantKey, inventoryDataSourcesMap);
    }
}