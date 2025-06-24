package com.example.scenario.service;

import com.example.scenario.aop.DistributedLock;
import com.example.scenario.dto.BuyCouponResult;
import com.example.scenario.entity.Coupon;
import com.example.scenario.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    @Transactional
    public void decrease(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(IllegalArgumentException::new);

        coupon.decrease();
    }

    @DistributedLock(key = "#lockName")
    public void decrease(String lockName, Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(IllegalArgumentException::new);

        coupon.decrease();
    }

    public Coupon createCoupon() {
        Coupon coupon = Coupon.builder().build();
        couponRepository.save(coupon);
        return coupon;
    }
}
