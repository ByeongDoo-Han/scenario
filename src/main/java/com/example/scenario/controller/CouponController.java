package com.example.scenario.controller;

import com.example.scenario.dto.BuyCouponResult;
import com.example.scenario.entity.Coupon;
import com.example.scenario.repository.CouponRepository;
import com.example.scenario.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/coupons")
    public ResponseEntity<Coupon> getCoupon(){
        return ResponseEntity.ok(couponService.getCoupon());
    }

    @PostMapping("/coupons")
    public ResponseEntity<Coupon> createCoupon(){
        return ResponseEntity.ok(couponService.createCoupon());
    }

    @PostMapping("/coupon/{id}")
    public ResponseEntity<BuyCouponResult> buyCoupon(@PathVariable Long id, @RequestParam Long userId){
        return ResponseEntity.ok(couponService.buyCoupon(id, userId));
    }
}
