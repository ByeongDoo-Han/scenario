package com.example.scenario.controller;

import com.example.scenario.entity.Human;
import com.example.scenario.entity.Product;
import com.example.scenario.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/elastic")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String q){
        System.out.println("검색 키워드 : "+q);
        return searchService.searchProduct(q);
    }

    @PostMapping("/products")
    public void saveProduct(@RequestBody Product product) {
        searchService.saveProduct(product);
    }
    @GetMapping("/human2")
    public ResponseEntity<List<Human>> searchHuman(@RequestParam String q){
        System.out.println("검색 키워드 : "+q);
        return searchService.searchHuman(q);
    }

    @GetMapping("/human")
    public ResponseEntity<List<Human>> searchHuman2(@RequestParam String q){
        System.out.println("검색 키워드 : "+q);
        return searchService.searchHuman2(q);
    }

    @PostMapping("/human")
    public void saveHuman(@RequestBody Human human) {
        searchService.saveHuman(human);
    }

    @GetMapping("/products/all")
    public ResponseEntity<List<Product>> searchAllProducts(){
        return searchService.searchAllProducts();
    }

    @GetMapping("/human/all")
    public ResponseEntity<List<Human>> searchAllHuman(){
        return searchService.searchAllHuman();
    }

    @DeleteMapping("/products")
    public void deleteByProductsId(@RequestParam final String id){
        searchService.deleteByProductsId(id);
    }

    @DeleteMapping("/products/{title}")
    public void deleteByProductsTitle(@PathVariable String title){
        searchService.deleteByProductsTitle(title);
    }

    @DeleteMapping("/human")
    public void deleteByHumanId(@RequestParam final String id){
        searchService.deleteByHumanId(id);
    }

    @DeleteMapping("/human/{name}")
    public void deleteByHumanName(@PathVariable String name){
        searchService.deleteByHumanName(name);
    }
}
