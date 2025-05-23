package com.example.scenario.service;

import com.example.scenario.entity.Product;
import com.example.scenario.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ProductRepository productRepository;

    public List<Product> search(String keyword) {
        return productRepository.findByTitleContaining(keyword);
    }

    public void save(final Product product) {
        productRepository.save(product);
    }
}
