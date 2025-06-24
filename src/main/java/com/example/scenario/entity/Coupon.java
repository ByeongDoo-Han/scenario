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

    private static final String COUPON_NAME = "10% 할인 쿠폰";

    @Builder
    public Coupon(String couponName, Long quantity){
        this.couponName = createCouponName();
        this.quantity = quantity;
    }

    public void decrease() {
        validateStockCount();
        this.quantity -= 1;
    }

    private void validateStockCount(){
        if (quantity < 1){
            throw new IllegalArgumentException();
        }
    }

    private String createCouponName(){
        return COUPON_NAME;
    }
}
