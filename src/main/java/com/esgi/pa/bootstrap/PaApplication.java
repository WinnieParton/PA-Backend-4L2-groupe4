package com.esgi.pa.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.esgi.pa")
@EnableJpaRepositories("com.esgi.pa.server.repositories")
@EntityScan(basePackages = "com.esgi.pa.domain.entities")
public class PaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaApplication.class, args);
	}

}
