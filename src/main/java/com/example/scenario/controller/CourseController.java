package com.example.scenario.controller;

import com.example.scenario.dto.SubmitLectureResult;
import com.example.scenario.entity.Course;
import com.example.scenario.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getLastFiveCourse(){
        return ResponseEntity.ok(courseService.getLastFiveCourse());
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(){
        return ResponseEntity.ok(courseService.createCourse());
    }

    @PostMapping("/course/{id}")
    public ResponseEntity<SubmitLectureResult> buyCourse(@PathVariable Long id, @RequestParam Long userId){
        return ResponseEntity.ok(courseService.buyCourse(id, userId));
    }

    @PostMapping("/course/notsync/{id}")
    public ResponseEntity<SubmitLectureResult> notSyncBuyCourse(@PathVariable Long id, @RequestParam Long userId){
        return ResponseEntity.ok(courseService.notSyncBuyCourse(id, userId));
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<Long> buyCourse(@PathVariable Long id){
        return ResponseEntity.ok(courseService.getQuantity(id));
    }
}
