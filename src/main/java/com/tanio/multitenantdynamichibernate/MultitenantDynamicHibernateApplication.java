package com.tanio.multitenantdynamichibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MultitenantDynamicHibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultitenantDynamicHibernateApplication.class, args);
	}

	@GetMapping("/hello")
	String hello() {
		return "hello";
	}

}
