package com.example.scenario.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.example.scenario.entity.Product;
import com.example.scenario.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ProductRepository productRepository;

    public ResponseEntity<List<Product>> search(String keyword) {
        return ResponseEntity.ok(productRepository.findByTitle(keyword));
    }

    public void save(final Product product) {
        productRepository.save(product);
    }

}
