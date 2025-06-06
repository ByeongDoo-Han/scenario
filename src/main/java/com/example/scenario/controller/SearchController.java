package com.example.scenario.controller;

import com.example.scenario.entity.Product;
import com.example.scenario.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam String q){
        System.out.println("검색 키워드 : "+q);
        return searchService.search(q);
    }

    @PostMapping("/search/save")
    public void save(@RequestBody Product product) {
        searchService.save(product);
    }

    @GetMapping("/search/all")
    public ResponseEntity<List<Product>> searchAll(){
        return searchService.searchAll();
    }

    @DeleteMapping("/search/delete")
    public void deleteById(@RequestParam final String id){
        searchService.deleteById(id);
    }

    @DeleteMapping("/search/delete/{title}")
    public void deleteByTitle(@PathVariable String title){
        searchService.deleteByTitle(title);
    }
}
