package com.app.proteinjakt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProteinjaktApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProteinjaktApplication.class, args);
	}

}
