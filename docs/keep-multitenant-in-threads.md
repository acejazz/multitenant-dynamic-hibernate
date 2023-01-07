# How to keep multitenant in Threads

`CustomRequestScopeAttr.java`:
```java
public class CustomRequestScopeAttr implements RequestAttributes {
    private final Map<String, Object> requestAttributeMap = new HashMap<>();

    @Override
    public Object getAttribute(String name, int scope) {
        if (scope == RequestAttributes.SCOPE_REQUEST) {
            return this.requestAttributeMap.get(name);
        }
        return null;
    }

    @Override
    public void setAttribute(String name, Object value, int scope) {
        if (scope == RequestAttributes.SCOPE_REQUEST) {
            this.requestAttributeMap.put(name, value);
        }
    }

    @Override
    public void removeAttribute(String name, int scope) {
        if (scope == RequestAttributes.SCOPE_REQUEST) {
            this.requestAttributeMap.remove(name);
        }
    }

    @Override
    public String[] getAttributeNames(int scope) {
        if (scope == RequestAttributes.SCOPE_REQUEST) {
            return this.requestAttributeMap.keySet().toArray(new String[0]);
        }
        return new String[0];
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback, int scope) {
        // Not Supported
    }

    @Override
    public Object resolveReference(String key) {
        // Not supported
        return null;
    }

    @Override
    public String getSessionId() {
        return null;
    }

    @Override
    public Object getSessionMutex() {
        return null;
    }
}
```

`Application.java`:

```java
package com.tanio.multitenant;

import com.tanio.multitenant.customers.Customer;
import com.tanio.multitenant.customers.CustomerRepository;
import com.tanio.multitenant.inventory.Car;
import com.tanio.multitenant.inventory.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import javax.sql.DataSource;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@RestController
public class Application implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CombinedDataSourceService combinedDataSourceService;

    @Autowired
    CurrentCombinedDataSource currentCombinedDatasource;

    int counter = 0;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/save-customer")
    Customer saveCustomer() {
        CombinedDataSource combinedDataSource = currentCombinedDatasource.get(); // We need the current datasource

        IntStream.range(0, 5).parallel().forEach(it -> saveSingleCustomer(combinedDataSource));
        Customer tanio = customerRepository.save(new Customer(UUID.randomUUID().toString(), "Tanio", "Messina" + counter++));
        RequestContextHolder.resetRequestAttributes();
        return tanio;
    }

    void saveSingleCustomer(CombinedDataSource combinedDataSource) {
        RequestContextHolder.setRequestAttributes(new CustomRequestScopeAttr()); // We set the request bean
        currentCombinedDatasource.set(combinedDataSource);

        customerRepository.save(new Customer(UUID.randomUUID().toString(), "Tanio", "Messina" + counter++));
    }

    @GetMapping("/save-car")
    Car saveCar() {
        CombinedDataSource combinedDataSource = currentCombinedDatasource.get();

        IntStream.range(0, 5).parallel().forEach(it -> saveSingleCar(combinedDataSource));
        Car save = carRepository.save(new Car(UUID.randomUUID().toString(), "Opel" + counter++));
        RequestContextHolder.resetRequestAttributes();
        return save;
    }

    void saveSingleCar(CombinedDataSource combinedDataSource) {
        RequestContextHolder.setRequestAttributes(new CustomRequestScopeAttr());
        currentCombinedDatasource.set(combinedDataSource);
        carRepository.save(new Car(UUID.randomUUID().toString(), "Opel" + counter++));
    }

    @Override
    public void run(String... args) {
        DataSource firstCustomerDataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/customers")
                .username("root")
                .password("tanio")
                .build();

        DataSource firstInventoryDataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3307/inventory")
                .username("root")
                .password("tanio")
                .build();

        CombinedDataSource first = new CombinedDataSource(firstCustomerDataSource, firstInventoryDataSource);
        combinedDataSourceService.addCombinedDataSource(first, "1");

        DataSource secondCustomerDataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3308/customers")
                .username("root")
                .password("tanio")
                .build();

        DataSource secondInventoryDataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3309/inventory")
                .username("root")
                .password("tanio")
                .build();

        CombinedDataSource second = new CombinedDataSource(secondCustomerDataSource, secondInventoryDataSource);
        combinedDataSourceService.addCombinedDataSource(second, "2");
    }
}
```