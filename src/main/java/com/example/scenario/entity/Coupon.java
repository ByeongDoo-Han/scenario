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
    public Coupon(String couponName, Long quantity){
        this.couponName = couponName;
        this.quantity = quantity;
    }

    public void decrease() {
        validateStockCount();
        this.quantity -= 1;
    }

    private void validateStockCount(){
        if (quantity < 1){
            throw new IllegalArgumentException("수량 초과");
        }
    }



}
