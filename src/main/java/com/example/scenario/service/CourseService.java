package com.example.scenario.service;

import com.example.scenario.dto.SubmitLectureResult;
import com.example.scenario.entity.Course;
import com.example.scenario.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private static final Logger log = LoggerFactory.getLogger(CourseService.class);

    public synchronized void decrease(final Long id, final Long quantity){
        Course course = courseRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("존재하지 않는 강의입니다.")
        );
        course.decrease(quantity);
        courseRepository.saveAndFlush(course);
    }

    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    public Course createCourse() {
        Course course = Course.builder()
                .build();
        courseRepository.save(course);
        return course;
    }

//    public Course getCourse() {
//        return courseRepository.findByCourseName("싸이 콘서트");
//    }

    public synchronized SubmitLectureResult buyCourse(final Long id, final Long userId) {
        Course course = courseRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("존재하지 않는 쿠폰입니다.")
        );
        try{
            course.decrease(1L);
            courseRepository.save(course);
            log.info("userId: {} ✅ 쿠폰 발급 성공", userId);
            return new SubmitLectureResult(userId, "success");
        } catch (RuntimeException e){
            log.info("userId: {} ❌ 쿠폰 재고 없음", userId);
            return new SubmitLectureResult(userId,"fail");
        }
    }
}
