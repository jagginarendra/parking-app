package com.parking.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.parking.app.repository")
@EntityScan("com.parking.app.model")
public class ParkingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingAppApplication.class, args);
	}

}
