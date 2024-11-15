package com.project3.project3;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Project3Application {

    public static void main(String[] args) {
        // Load Dotenv only if JWT_SECRET is not already set by the system environment
        if (System.getenv("JWT_SECRET") == null) {
            Dotenv dotenv = Dotenv.configure().load();
            System.out.println("JWT_SECRET (Dotenv): " + dotenv.get("JWT_SECRET"));
        } else {
            System.out.println("JWT_SECRET (System): " + System.getenv("JWT_SECRET"));
        }
        
        SpringApplication.run(Project3Application.class, args);
    }
}
