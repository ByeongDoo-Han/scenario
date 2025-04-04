package com.example.scenario.service;

import com.example.scenario.entity.Coupon;
import com.example.scenario.repository.CouponRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CouponServiceTest {

    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepository couponRepository;

//    public static String TEN_PERCENT_DISCOUNT = "10% 할인 쿠폰";

    @BeforeEach
    void before(){
        Coupon coupon = new Coupon(1L, "10퍼 할인 쿠폰", 100L);
        couponRepository.saveAndFlush(coupon);
        System.out.println("쿠폰 저장");
    }

    @AfterEach
    void after(){
        couponRepository.deleteAll();
    }

    @Test
    @DisplayName("한개 요청")
    void couponDecrease() {
        couponService.decrease(1L, 1L);
        Coupon coupon = couponRepository.findById(1L).orElseThrow();
        assertEquals(99L, coupon.getQuantity());
//        assertThat(coupon.getQuantity()).isEqualTo(99L);
    }

    @Test
    @DisplayName("100개 동시 요청")
    void couponDecrease100() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(()->{
                try{
                    couponService.decrease(1L,1L);
                }
                finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        Coupon coupon = couponRepository.findById(1L).orElseThrow();
        assertEquals(0L,coupon.getQuantity());
//        assertThat(coupon.getQuantity()).isEqualTo(0L);
    }
}