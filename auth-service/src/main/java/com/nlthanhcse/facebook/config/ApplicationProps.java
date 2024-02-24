package com.nlthanhcse.facebook.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProps {
    @Bean
    @ConfigurationProperties(prefix = "jwt")
    public JwtProps jwtProps() {
        return new JwtProps();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JwtProps {
        private String secretKey;
    }
}
