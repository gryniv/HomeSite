package com.home.site.config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;

@Configuration
public class EncryptionConfig {
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
