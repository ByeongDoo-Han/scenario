package com.example.scenario.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry cors){
        cors.addMapping("/**")
            .allowedOrigins(
                "http://localhost:5173",
                "http://localhost:80",
                "http://localhost:8080",
                "http://localhost:9200",
                "http://localhost",
                "https://scenario-front-phi.vercel.app",
                "https://www.dewdew.site",
                "https://dewdew.site"
            )
            .allowedMethods("GET", "POST", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true);
    }

}
