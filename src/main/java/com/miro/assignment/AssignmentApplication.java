package com.miro.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AssignmentApplication {

	/**
	 * The main entry point of the application
	 * @param args No arguments are processed
	 */
	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}

}
