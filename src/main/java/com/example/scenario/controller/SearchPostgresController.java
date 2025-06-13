package com.example.scenario.controller;

import com.example.scenario.entity.HumanPostgres;
import com.example.scenario.repository.HumanPostgresRepository;
import com.example.scenario.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/postgres")
@RequiredArgsConstructor
public class SearchPostgresController {

    private final SearchService searchService;

    @GetMapping("/human")
    public ResponseEntity<HumanPostgres> findHumanByName(@RequestParam String name){
        return ResponseEntity.ok(searchService.findByName(name));
    }
}
