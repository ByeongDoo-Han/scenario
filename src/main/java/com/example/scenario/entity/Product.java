package com.example.scenario.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "products")
@NoArgsConstructor
@Getter
@Setting(settingPath = "/elasticsearch/settings/product-settings.json")
public class Product {

    @Id
    private String id;
    @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "autocomplete_search")
    private String title;

    public Product(final String id, final String q) {
        this.id = id;
        this.title = q;
    }
}
