package com.project3.project3;

import com.project3.project3.utility.BadgeInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Project3Application {

	public static void main(String[] args) {
		SpringApplication.run(Project3Application.class, args);

		// Call the static method directly to initialize badges
		BadgeInitializer.initializeBadges();
	}
}


