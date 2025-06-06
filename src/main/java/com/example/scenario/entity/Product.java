package com.example.scenario.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "products")
@NoArgsConstructor
@Getter
public class Product {

    @Id
    private String id;
    @Field(type = FieldType.Text, analyzer = "ngram_analyzer", searchAnalyzer = "ngram_search_analyzer")
    private String title;

    public Product(final String id, final String q) {
        this.id = id;
        this.title = q;
    }
}
