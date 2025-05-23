package com.example.scenario.controller;

import com.example.scenario.entity.Product;
import com.example.scenario.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public List<Product> search(@RequestParam String q){
        return searchService.search(q);
    }

    @PostMapping("/save")
    public void save(@RequestBody Product product){
        searchService.save(product);
    }
}
