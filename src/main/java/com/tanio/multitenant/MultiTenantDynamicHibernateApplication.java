package com.tanio.multitenant;

import com.tanio.multitenant.first.Person;
import com.tanio.multitenant.first.PersonRepository;
import com.tanio.multitenant.second.Car;
import com.tanio.multitenant.second.CarRepository;
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
	PersonRepository personRepository;

	@Autowired
	CarRepository carRepository;

	public static void main(String[] args) {
		SpringApplication.run(MultiTenantDynamicHibernateApplication.class, args);
	}

	@GetMapping("/save-person")
	Person savePerson() {
		log.info("Current tenant: [{}]", TenantContext.getCurrentTenant());
		return personRepository.save(new Person(UUID.randomUUID().toString(), "Tanio", "Messina"));
	}

	@GetMapping("/save-car")
	Car saveCar() {
		log.info("Current tenant: [{}]", TenantContext.getCurrentTenant());
		return carRepository.save(new Car(UUID.randomUUID().toString(), "Opel"));
	}

}
