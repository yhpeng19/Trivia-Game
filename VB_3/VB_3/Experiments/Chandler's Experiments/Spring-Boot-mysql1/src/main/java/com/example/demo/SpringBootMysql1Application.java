package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "repository")
@SpringBootApplication
public class SpringBootMysql1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMysql1Application.class, args);
	}

}
