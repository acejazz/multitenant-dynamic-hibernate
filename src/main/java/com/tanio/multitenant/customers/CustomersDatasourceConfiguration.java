package com.tanio.multitenant.customers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "customersLocalContainerEntityManagerFactoryBean",
        transactionManagerRef = "customersPlatformTransactionManager")
class CustomersDatasourceConfiguration {

    @Bean(name = "customersLocalContainerEntityManagerFactoryBean")
    LocalContainerEntityManagerFactoryBean customersLocalContainerEntityManagerFactoryBean(
            @Qualifier("customersDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.tanio.multitenant.customers");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return em;
    }

    @Bean(name = "customersPlatformTransactionManager")
    PlatformTransactionManager customersPlatformTransactionManager(
            @Qualifier("customersDataSource") DataSource dataSource) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(
                customersLocalContainerEntityManagerFactoryBean(dataSource)
                        .getObject());
        return tm;
    }
}
