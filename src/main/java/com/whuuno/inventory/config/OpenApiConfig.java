package com.whuuno.inventory.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI inventoryOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Development Server");

        Contact contact = new Contact();
        contact.setEmail("inventory@example.com");
        contact.setName("Inventory Team");
        contact.setUrl("https://www.example.com");

        License license = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Inventory Management API")
                .version("1.0.0")
                .contact(contact)
                .description("RESTful API for managing inventory items with CRUD operations and JWT authentication")
                .termsOfService("https://www.example.com/terms")
                .license(license);

        // JWT Security Scheme
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("JWT Authentication")
                .description("Enter JWT token obtained from /auth/login endpoint");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer))
                .addSecurityItem(securityRequirement)
                .components(new Components().addSecuritySchemes("Bearer Authentication", securityScheme));
    }
}
