package com.example.scenario.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "human")
@NoArgsConstructor
@Getter
@Setting(settingPath = "/elasticsearch/settings/human-settings.json")
public class Human {
    @Id
    private String id;
    @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "autocomplete_search")
    private String name;

    public Human(final String id, final String name) {
        this.id = id;
        this.name = name;
    }
}