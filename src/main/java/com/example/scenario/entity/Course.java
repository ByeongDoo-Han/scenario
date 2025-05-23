package com.example.scenario.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Getter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private Long quantity;
    private String professor;
    private static final List<String> COURSE_LIST = Arrays.asList(
        "데이터베이스 시스템",
        "컴퓨터 네트워크",
        "알고리즘",
        "데이터 구조",
        "운영체제",
        "컴파일러 설계",
        "소프트웨어 공학",
        "인공지능",
        "컴퓨터 보안",
        "클라우드 컴퓨팅",
        "데이터 마이닝",
        "기계학습",
        "데이터베이스 설계",
        "네트워크 보안",
        "웹 프로그래밍",
        "모바일 애플리케이션 개발",
        "게임 프로그래밍",
        "그래픽스",
        "임베디드 시스템",
        "디지털 신호 처리");
    private final static List<Character> FIRST_NAME_LIST = Arrays.asList(
        '김', '이', '박', '최', '정', '강', '조', '윤', '장', '임'
    );
    private final static List<Character> SECOND_NAME_LIST = Arrays.asList(
        '민', '지', '수', '성', '준', '경', '현', '준', '지', '은'
    );
    private final static List<Character> FINAL_NAME_LIST = Arrays.asList(
        '준', '민', '지', '수', '성', '현', '준', '경', '은', '아'
    );

    private static final Random RANDOM = new Random();

    public String setRandomCourse() {
        return COURSE_LIST.get(RANDOM.nextInt(COURSE_LIST.size()));
    }

    public String setRandomProfessor(){
        Character a = FIRST_NAME_LIST.get(RANDOM.nextInt(FIRST_NAME_LIST.size()));
        Character b = SECOND_NAME_LIST.get(RANDOM.nextInt(SECOND_NAME_LIST.size()));
        Character c = FINAL_NAME_LIST.get(RANDOM.nextInt(FINAL_NAME_LIST.size()));
        return String.valueOf(a) + b + c + " 교수님";
    }

    public void decrease(Long quantity) {
        if (this.quantity - quantity < 0) {
            throw new RuntimeException("수강 인원이 초과됐습니다.");
        }
        this.quantity -= quantity;
    }

    public String setCode(){
        return "C0" + String.valueOf(RANDOM.nextInt(99));
    }

    public Long setQuantity(){
        return RANDOM.nextLong(1L,20L);
    }

    @Builder
    public Course(){
        this.code = setCode();
        this.name = setRandomCourse();
        this.quantity = setQuantity();
        this.professor = setRandomProfessor();
    }
}
