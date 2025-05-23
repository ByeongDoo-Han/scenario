package com.example.scenario.repository;

import com.example.scenario.entity.Coupon;
import com.example.scenario.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findLastFiveByOrderByIdDesc();
    List<Course> findTop5ByOrderByIdDesc();
}