package com.tanio.multitenant.customers;

import com.tanio.multitenant.CurrentCombinedDatasource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static com.tanio.multitenant.customers.CustomersDatasourceConfiguration.ENTITY_MANAGER_FACTORY;
import static com.tanio.multitenant.customers.CustomersDatasourceConfiguration.TRANSACTION_MANAGER;
import static java.util.Collections.singletonMap;
import static java.util.Objects.requireNonNull;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = ENTITY_MANAGER_FACTORY,
        transactionManagerRef = TRANSACTION_MANAGER)
class CustomersDatasourceConfiguration {
    static final String ENTITY_MANAGER_FACTORY = "customersLocalContainerEntityManagerFactoryBean";
    static final String TRANSACTION_MANAGER = "customersPlatformTransactionManager";

    @Bean
    CustomersDynamicDatasource customersDataSource(CurrentCombinedDatasource currentDataSources) {
        return new CustomersDynamicDatasource(currentDataSources::get);
    }

    @Bean(name = ENTITY_MANAGER_FACTORY)
    LocalContainerEntityManagerFactoryBean entityManagerFactory(CustomersDynamicDatasource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setJpaPropertyMap(singletonMap("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect"));
        em.setPackagesToScan("com.tanio.multitenant.customers");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        return em;
    }

    @Bean(name = TRANSACTION_MANAGER)
    PlatformTransactionManager transactionManager(
            @Qualifier(ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(requireNonNull(entityManagerFactory.getObject()));
    }
}