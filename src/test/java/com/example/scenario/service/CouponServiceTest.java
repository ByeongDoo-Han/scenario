package com.example.scenario.service;

import com.example.scenario.entity.Coupon;
import com.example.scenario.repository.CouponRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void before(){
        Coupon coupon = new Coupon(1L,"10퍼 할인 쿠폰", 1000L);
        couponRepository.saveAndFlush(coupon);
        System.out.println("쿠폰 저장");
    }

    @Test
    @DisplayName("한개 요청")
    void couponDecrease() {
        couponService.decrease(1L, 1L);
        Coupon coupon = couponRepository.findById(1L).orElseThrow();
        assertEquals(999L, coupon.getQuantity());
    }

    @Test
    @DisplayName("100개 동시 요청")
     void couponDecrease100() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(1000);

        CountDownLatch latch = new CountDownLatch(threadCount);

        long start = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(()->{
                try{
                    couponService.decrease(1L,1L);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();
        long end = System.currentTimeMillis();
        Coupon coupon = couponRepository.findById(1L).orElseThrow();
        assertEquals(0L,coupon.getQuantity());
        System.out.println("실행 시간: "+(end-start)+" ms");
    }
}