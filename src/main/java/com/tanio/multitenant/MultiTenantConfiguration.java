package com.tanio.multitenant;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiTenantConfiguration {
    @Bean
    public DataSource dataSource() {
        DataSource customerDatasource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/customers")
                .username("root")
                .password("tanio")
                .build();

        DataSource inventoryDatasource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3307/inventory")
                .username("root")
                .password("tanio")
                .build();

        Map<Object, Object> datasources = new HashMap<>();
        datasources.put("1", customerDatasource);
        datasources.put("2", inventoryDatasource);

        MultiTenantDataSource multitenantDataSource = new MultiTenantDataSource();
        multitenantDataSource.setDefaultTargetDataSource(customerDatasource);
        multitenantDataSource.setTargetDataSources(datasources);
        multitenantDataSource.afterPropertiesSet();
        return multitenantDataSource;
    }
}