package com.example.scenario.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.example.scenario.entity.Human;
import com.example.scenario.entity.HumanPostgres;
import com.example.scenario.entity.Product;
import com.example.scenario.repository.HumanPostgresRepository;
import com.example.scenario.repository.HumanRepository;
import com.example.scenario.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ProductRepository productRepository;
    private final HumanRepository humanRepository;
    private final HumanPostgresRepository humanPostgresRepository;
    private final WebClient webClient;
    public ResponseEntity<List<Product>> searchProduct(String keyword) {
        return ResponseEntity.ok(productRepository.findByTitle(keyword));
    }
    public ResponseEntity<List<Human>> searchHuman(String name) {
        return ResponseEntity.ok(humanRepository.findByName(name));
    }

    public ResponseEntity<List<Human>> searchHuman2(String q) {
        String url = "http://localhost:9200/human/_search?size=10&pretty";
        Map<String ,Map<String ,Map<String, String >>> map = new HashMap<>();
        map.put("query", new HashMap<>());
        map.get("query").put("match",new HashMap<>());
        map.get("query").get("match").put("name",q);
        String response = webClient
            .method(HttpMethod.GET)
            .uri(url)
            .body(BodyInserters.fromValue(map))
            .retrieve()
            .bodyToMono(String.class)
            .block();
        System.out.println(response);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            List<String> names = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            List<Human> humans = new ArrayList<>();
            for (JsonNode hit : root.path("hits").path("hits")) {
                String id = hit.path("_id").asText();
                String name = hit.path("_source").path("name").asText();
                Human human = new Human(id, name);
                humans.add(human);
            }
            return ResponseEntity.ok(humans);
        } catch (Exception e) {
            throw new RuntimeException("Elasticsearch 파싱 실패", e);
        }
    }

    public void saveProduct(final Product product) {
        productRepository.save(product);
    }
    public void saveHuman(final Human human) {
        humanRepository.save(human);
    }

    public ResponseEntity<List<Product>> searchAllProducts() {
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

    
    public ResponseEntity<List<Human>> searchAllHuman() {
        String url = "http://localhost:9200/human/_search?size=1000&pretty";

        String response = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        System.out.println(response);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            List<String> names = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            List<Human> humans = new ArrayList<>();
            for (JsonNode hit : root.path("hits").path("hits")) {
                String id = hit.path("_id").asText();
                String name = hit.path("_source").path("name").asText();
                Human human = new Human(id, name);
                humans.add(human);
            }
            return ResponseEntity.ok(humans);
        } catch (Exception e) {
            throw new RuntimeException("Elasticsearch 파싱 실패", e);
        }
    }

    public void deleteByProductsId(final String id) {
        if(productRepository.findById(id).isEmpty()) return;
        productRepository.deleteById(id);
        System.out.println(id + " 삭제");
    }

    public void deleteByProductsTitle(final String title) {
        productRepository.deleteByTitle(title);
    }
    public void deleteByHumanId(final String id) {
        if(humanRepository.findById(id).isEmpty()) return;
        humanRepository.deleteById(id);
        System.out.println(id + " 삭제");
    }

    public void deleteByHumanName(final String name) {
        humanRepository.deleteByName(name);
    }

    public HumanPostgres findByName(final String name){
        return humanPostgresRepository.findByName(name);
    }
}
