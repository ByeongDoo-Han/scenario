package com.example.scenario.repository;

import com.example.scenario.entity.Human;
import com.example.scenario.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HumanRepository extends ElasticsearchRepository<Human, String> {
    List<Human> findByName(String name);
    void deleteByName(String name);
}
