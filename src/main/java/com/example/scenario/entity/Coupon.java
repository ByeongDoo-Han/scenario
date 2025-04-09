package com.example.scenario.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String couponName;
    private Long quantity;

    @Builder
    public Coupon(Long id, String couponName, Long quantity){
        this.id = id;
        this.couponName = couponName;
        this.quantity = quantity;
    }

    public void decrease(Long quantity) {
        if (this.quantity - quantity < 0) {
            throw new RuntimeException("쿠폰 재고가 부족합니다.");
        }
        this.quantity -= quantity;
    }
}
