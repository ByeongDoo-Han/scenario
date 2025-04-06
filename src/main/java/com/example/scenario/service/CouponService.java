package com.example.scenario.service;

import com.example.scenario.entity.Coupon;
import com.example.scenario.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;

    public synchronized void decrease(final Long id, final Long quantity){
        Coupon coupon = couponRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("존재하지 않는 쿠폰입니다.")
        );
        coupon.decrease(quantity);
        couponRepository.saveAndFlush(coupon);
    }
}
