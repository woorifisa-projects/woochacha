package com.woochacha.backend.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://localhost:3000","http://localhost:5500",
                       "https://web.postman.co/", "http://15.165.239.79:3000", "http://13.125.32.208:8080",
                        "https://15.165.239.79:3000", "https://13.125.32.208:8080") // 허용할 출처
                .allowedMethods("GET", "POST", "PATCH") // 허용할 HTTP method
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .maxAge(3000);
    }
}