package com.nunezdev.inventory_manager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JwtSecurityConfig {

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }
}
