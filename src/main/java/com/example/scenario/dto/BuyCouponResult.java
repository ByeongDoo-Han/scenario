package com.example.scenario.dto;

import lombok.Getter;

@Getter
public class BuyCouponResult {
    private final Long userId;
    private final String result;

    public BuyCouponResult(Long userId, String result){
        this.userId = userId;
        this.result = result;
    }
}
