package com.tanio.multitenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MultitenantDynamicHibernateApplication {

	@Autowired
	PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(MultitenantDynamicHibernateApplication.class, args);
	}

	@GetMapping("/hello")
	String hello() {
		personRepository.save(new Person(null, "Tanio", "Messina"));
		return "hello";
	}

}
