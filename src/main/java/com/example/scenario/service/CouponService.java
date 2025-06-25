package com.example.scenario.service;

import com.example.scenario.aop.DistributedLock;
import com.example.scenario.dto.BuyCouponResult;
import com.example.scenario.dto.SubmitLectureResult;
import com.example.scenario.entity.Coupon;
import com.example.scenario.entity.Course;
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
    private static final Logger log = LoggerFactory.getLogger(CouponService.class);
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
        Coupon coupon = Coupon.builder().couponName("10% 할인 쿠폰")
            .quantity(1000L).build();
        couponRepository.save(coupon);
        return coupon;
    }

    public Long getQuantity(final Long id) {
        Coupon coupon = couponRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
        return coupon.getQuantity();
    }

    @DistributedLock(key = "#userId")
    public SubmitLectureResult buyCoupon(final Long id, final Long userId) {
        Coupon coupon = couponRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("존재하지 않는 쿠폰입니다..")
        );
        try{
            coupon.decrease();
            couponRepository.save(coupon);
            log.info("userId: {} ✅ 쿠폰 발급 성공", userId);
            return new SubmitLectureResult(userId, "success");
        } catch (RuntimeException e){
            log.info("userId: {} ❌ 쿠폰 발급 실패", userId);
            return new SubmitLectureResult(userId,"fail");
        }
    }

    public synchronized SubmitLectureResult notSyncBuyCoupon(final Long id, final Long userId) {
        Coupon coupon = couponRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("존재하지 않는 쿠폰입니다..")
        );
        try{
            coupon.decrease();
            couponRepository.save(coupon);
            log.info("userId: {} ✅ 수강신청 성공", userId);
            return new SubmitLectureResult(userId, "success");
        } catch (RuntimeException e){
            log.info("userId: {} ❌ 수강신청 실패", userId);
            return new SubmitLectureResult(userId,"fail");
        }
    }

//    public Coupon notSyncBuyCoupon(final Long id, final Long userId) {
//
//    }
}
