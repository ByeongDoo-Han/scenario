package com.example.scenario.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.example.scenario.entity.Product;
import com.example.scenario.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ProductRepository productRepository;
    private final WebClient webClient;
    public ResponseEntity<List<Product>> search(String keyword) {
        return ResponseEntity.ok(productRepository.findByTitle(keyword));
    }

    public void save(final Product product) {
        productRepository.save(product);
    }

    public ResponseEntity<List<Product>> searchAll() {
        String url = "http://localhost:9200/products/_search?size=1000&pretty";

        String response = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        System.out.println(response);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            List<String> titles = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            List<Product> products = new ArrayList<>();
            for (JsonNode hit : root.path("hits").path("hits")) {
                String id = hit.path("_id").asText();
                String title = hit.path("_source").path("title").asText();
                Product product = new Product(id, title);
                products.add(product);
            }

            return ResponseEntity.ok(products);
        } catch (Exception e) {
            throw new RuntimeException("Elasticsearch 파싱 실패", e);
        }
    }

    public void deleteById(final String id) {
        if(productRepository.findById(id).isEmpty()) return;
        productRepository.deleteById(id);
        System.out.println(id + " 삭제");
    }

    public void deleteByTitle(final String title) {

        productRepository.deleteByTitle(title);
    }
}
