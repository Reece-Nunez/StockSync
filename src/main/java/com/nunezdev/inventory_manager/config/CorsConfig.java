package com.nunezdev.inventory_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // You can customize this to allow more specific origins, HTTP methods, and headers
                registry.addMapping("/**") // Allows CORS for all paths
                        .allowedOrigins("*") // Allows all origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allows specified HTTP methods
                        .allowedHeaders("*") // Allows all headers
                        .allowCredentials(false); // Include credentials like cookies in CORS requests
            }
        };
    }
}
