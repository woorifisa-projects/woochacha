package com.woochacha.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PATCH", "OPTIONS", "PUT", "DELETE", "HEAD") // 허용할 HTTP method
                .allowedOrigins("access-control-allow-origin")
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .maxAge(3000);
    }
}