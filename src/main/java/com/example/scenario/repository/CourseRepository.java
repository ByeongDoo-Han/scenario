package com.example.scenario.repository;

import com.example.scenario.entity.Coupon;
import com.example.scenario.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}