package com.example.scenario.repository;

import com.example.scenario.entity.Human;
import com.example.scenario.entity.HumanPostgres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanPostgresRepository extends JpaRepository<HumanPostgres, Long > {
    HumanPostgres findByName(String name);
}
