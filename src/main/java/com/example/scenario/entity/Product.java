package com.example.scenario.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "products")
@Getter
public class Product {

    @Id
    private String id;
    @Field(type = FieldType.Text, analyzer = "ngram_analyzer", searchAnalyzer = "ngram_search_analyzer")
    private final String title;

    public Product(final String q) {
        this.title = q;
    }
}
