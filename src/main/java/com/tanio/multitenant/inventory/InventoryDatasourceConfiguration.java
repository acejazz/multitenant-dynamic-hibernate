package com.tanio.multitenant.inventory;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "inventoryLocalContainerEntityManagerFactoryBean",
        transactionManagerRef = "inventoryPlatformTransactionManager")
class InventoryDatasourceConfiguration {

    @Bean(name = "inventoryDatasource")
    DataSource inventoryDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3307/inventory")
                .username("root")
                .password("tanio")
                .build();
    }

    @Bean(name = "inventoryLocalContainerEntityManagerFactoryBean")
    LocalContainerEntityManagerFactoryBean inventoryLocalContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(inventoryDataSource());
        em.setPackagesToScan("com.tanio.multitenant.inventory");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean(name = "inventoryPlatformTransactionManager")
    PlatformTransactionManager inventoryPlatformTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(inventoryLocalContainerEntityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }
}
