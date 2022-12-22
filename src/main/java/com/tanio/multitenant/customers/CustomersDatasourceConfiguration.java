package com.tanio.multitenant.customers;

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
        entityManagerFactoryRef = "customersLocalContainerEntityManagerFactoryBean",
        transactionManagerRef = "customersPlatformTransactionManager")
class CustomersDatasourceConfiguration {

    @Bean(name = "customersDataSource")
    DataSource customersDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/customers")
                .username("root")
                .password("tanio")
                .build();
    }

    @Bean(name = "customersLocalContainerEntityManagerFactoryBean")
    LocalContainerEntityManagerFactoryBean customersLocalContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(customersDataSource());
        em.setPackagesToScan("com.tanio.multitenant.customers");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return em;
    }

    @Bean(name = "customersPlatformTransactionManager")
    PlatformTransactionManager customersPlatformTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(customersLocalContainerEntityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }
}
