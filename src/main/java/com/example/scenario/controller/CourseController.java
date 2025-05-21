package com.example.scenario.controller;

import com.example.scenario.dto.SubmitLectureResult;
import com.example.scenario.entity.Course;
import com.example.scenario.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CourseController {

    private final CourseService courseService;

//    @GetMapping("/courses")
//    public ResponseEntity<Course> getCourse(){
//        return ResponseEntity.ok(courseService.getCourse());
//    }

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(){
        return ResponseEntity.ok(courseService.createCourse());
    }

    @PostMapping("/course/{id}")
    public ResponseEntity<SubmitLectureResult> buyCourse(@PathVariable Long id, @RequestParam Long userId){
        return ResponseEntity.ok(courseService.buyCourse(id, userId));
    }
}
