package com.example.scenario.service;

import com.example.scenario.dto.BuyCouponResult;
import com.example.scenario.entity.Coupon;
import com.example.scenario.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private static final Logger log = LoggerFactory.getLogger(CouponService.class);
    public synchronized void decrease(final Long id, final Long quantity){
        Coupon coupon = couponRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("존재하지 않는 쿠폰입니다.")
        );
        coupon.decrease(quantity);
        couponRepository.saveAndFlush(coupon);
    }

    public List<Coupon> getAllCoupon() {
        return couponRepository.findAll();
    }

    public Coupon createCoupon() {
        couponRepository.deleteAll();
        Coupon coupon = Coupon.builder()
            .quantity(10L)
            .couponName("싸이 콘서트")
            .build();
        couponRepository.save(coupon);
        return coupon;
    }

    public Coupon getCoupon() {
        return couponRepository.findByCouponName("싸이 콘서트");
    }

    public synchronized BuyCouponResult buyCoupon(final Long id, final Long userId) {
        Coupon coupon = couponRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("존재하지 않는 쿠폰입니다.")
        );
        try{
            coupon.decrease(1L);
            couponRepository.save(coupon);
            log.info("userId: {} ✅ 쿠폰 발급 성공", userId);
            return new BuyCouponResult(userId, "success");
        } catch (RuntimeException e){
            log.info("userId: {} ❌ 쿠폰 재고 없음", userId);
            return new BuyCouponResult(userId,"fail");
        }
    }
}
