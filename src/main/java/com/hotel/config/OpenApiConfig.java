package com.hotel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .info(new Info().title("Reserva Hoteles API").version("1.0"))
                                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                                .components(new io.swagger.v3.oas.models.Components()
                                                .addSecuritySchemes("bearerAuth",
                                                                new SecurityScheme()
                                                                                .type(SecurityScheme.Type.HTTP)
                                                                                .scheme("bearer")
                                                                                .bearerFormat("JWT")));
        }
}