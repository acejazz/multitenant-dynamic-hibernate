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

import javax.sql.DataSource;
import java.util.UUID;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@RestController
public class Application implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CombinedDataSourceService combinedDataSourceService;

    int counter = 0;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/save-customer")
    Customer saveCustomer() {
        return customerRepository.save(new Customer(UUID.randomUUID().toString(), "Tanio", "Messina" + counter++));
    }

    @GetMapping("/save-car")
    Car saveCar() {
        return carRepository.save(new Car(UUID.randomUUID().toString(), "Opel" + counter++));
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