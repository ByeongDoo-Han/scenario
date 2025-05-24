package com.example.scenario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.example.scenario.repository")
public class ScenarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScenarioApplication.class, args);
    }

}
