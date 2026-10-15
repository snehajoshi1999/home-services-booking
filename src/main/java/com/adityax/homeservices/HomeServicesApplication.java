package com.adityax.homeservices;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Home Services Booking API", version = "1.0",
        description = "Browse home services and book them"))
public class HomeServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeServicesApplication.class, args);
    }
}
