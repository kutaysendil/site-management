package com.kutaysendil.siteManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SiteManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiteManagementApplication.class, args);
	}

}
