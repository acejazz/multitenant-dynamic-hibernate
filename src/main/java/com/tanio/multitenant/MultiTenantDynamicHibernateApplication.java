package com.tanio.multitenant;

import com.tanio.multitenant.customers.Customer;
import com.tanio.multitenant.customers.CustomerRepository;
import com.tanio.multitenant.inventory.Car;
import com.tanio.multitenant.inventory.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootApplication
@RestController
public class MultiTenantDynamicHibernateApplication {

	private static final Logger log = LoggerFactory.getLogger(MultiTenantDynamicHibernateApplication.class);

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CarRepository carRepository;

	public static void main(String[] args) {
		SpringApplication.run(MultiTenantDynamicHibernateApplication.class, args);
	}

	@GetMapping("/save-customer")
	Customer saveCustomer() {
		log.info("Current tenant: [{}]", TenantContext.getCurrentTenant());
		return customerRepository.save(new Customer(UUID.randomUUID().toString(), "Tanio", "Messina"));
	}

	@GetMapping("/save-car")
	Car saveCar() {
		log.info("Current tenant: [{}]", TenantContext.getCurrentTenant());
		return carRepository.save(new Car(UUID.randomUUID().toString(), "Opel"));
	}

}
