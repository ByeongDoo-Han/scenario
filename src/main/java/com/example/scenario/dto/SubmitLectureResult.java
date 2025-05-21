package com.example.scenario.dto;

import lombok.Getter;

@Getter
public class SubmitLectureResult {
    private final Long userId;
    private final String result;

    public SubmitLectureResult(Long userId, String result){
        this.userId = userId;
        this.result = result;
    }
}
