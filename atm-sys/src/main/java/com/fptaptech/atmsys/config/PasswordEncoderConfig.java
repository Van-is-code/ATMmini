package com.fptaptech.atmsys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public SCryptPasswordEncoder passwordEncoder() {
        // Adjust the parameters to ensure N is a power of 2 and greater than 1
        return new SCryptPasswordEncoder(16384, 8, 1, 32, 64);
    }
}