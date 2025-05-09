package com.example.scenario.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry cors){
        cors.addMapping("/**")
            .allowedOrigins("http://localhost:5173")
            .allowedOrigins("http://localhost:80")
            .allowedOrigins("http://localhost:8080")
            .allowedOrigins("http://localhost")
            .allowedOrigins("https://scenario-front-phi.vercel.app/")
            .allowedOrigins("https://www.dewdew.site/")
            .allowedOrigins("https://dewdew.site/")
            .allowedMethods("GET", "POST")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
