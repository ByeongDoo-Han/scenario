package com.example.scenario.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponDto {
    private String couponName;
    private Long quantity;

    @Builder
    public CouponDto(String couponName, Long quantity){
        this.couponName = couponName;
        this.quantity = quantity;
    }
}
