package com.tanio.multitenant.inventory;

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

import static java.util.Collections.singletonMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "inventoryLocalContainerEntityManagerFactoryBean",
        transactionManagerRef = "inventoryPlatformTransactionManager")
class InventoryDatasourceConfiguration {

    @Bean(name = "inventoryLocalContainerEntityManagerFactoryBean")
    LocalContainerEntityManagerFactoryBean inventoryLocalContainerEntityManagerFactoryBean(
            @Qualifier("inventoryDatasource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setJpaPropertyMap(singletonMap("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect"));
        em.setPackagesToScan("com.tanio.multitenant.inventory");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return em;
    }

    @Bean(name = "inventoryPlatformTransactionManager")
    PlatformTransactionManager inventoryPlatformTransactionManager(
            @Qualifier("inventoryDatasource") DataSource dataSource) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(
                        inventoryLocalContainerEntityManagerFactoryBean(dataSource)
                                .getObject());
        return tm;
    }
}
