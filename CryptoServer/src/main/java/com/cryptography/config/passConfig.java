package com.cryptography.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class passConfig{
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new Pbkdf2PasswordEncoder("kilid", 100, 512);
    }

}
