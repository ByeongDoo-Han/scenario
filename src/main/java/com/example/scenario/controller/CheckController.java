package com.example.scenario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CheckController {
    
    @GetMapping("/check")
    public String checkServer(){
        return "check";
    }
}
