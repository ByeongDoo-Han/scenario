package com.example.scenario.controller;

import com.example.scenario.dto.BuyCouponResult;
import com.example.scenario.dto.SubmitLectureResult;
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

    @PostMapping("/coupon")
    public ResponseEntity<Coupon> createCoupon(){
        return ResponseEntity.ok(couponService.createCoupon());
    }

    @PostMapping("/coupon/{id}")
    public ResponseEntity<SubmitLectureResult> buyCoupon(@PathVariable Long id, @RequestParam Long userId){
        return ResponseEntity.ok(couponService.buyCoupon(id, userId));
    }
//
    @PostMapping("/coupon/notsync/{id}")
    public ResponseEntity<SubmitLectureResult> notSyncBuyCoupon(@PathVariable Long id, @RequestParam Long userId){
        return ResponseEntity.ok(couponService.notSyncBuyCoupon(id, userId));
    }

    @GetMapping("/coupon/{id}")
    public ResponseEntity<Long> buyCourse(@PathVariable Long id){
        return ResponseEntity.ok(couponService.getQuantity(id));
    }
}
