package com.example.scenario.service;

import com.example.scenario.entity.Coupon;
import com.example.scenario.repository.CouponRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
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
    CouponService couponService;

    @Autowired
    CouponRepository couponRepository;


    @BeforeEach
    void setUp() {
        Coupon coupon = new Coupon("DEWDEW_001", 100L);
        couponRepository.save(coupon);
    }

    /**
     * Feature: 쿠폰 차감 동시성 테스트
     * Background
     *     Given KURLY_001 라는 이름의 쿠폰 100장이 등록되어 있음
     * <p>
     * Scenario: 100장의 쿠폰을 100명의 사용자가 동시에 접근해 발급 요청함
     *           Lock의 이름은 쿠폰명으로 설정함
     * <p>
     * Then 사용자들의 요청만큼 정확히 쿠폰의 개수가 차감되어야 함
     */
    @Test
    @DisplayName("쿠폰차감_분산락_적용_동시성100명_테스트")
    void decrease100() throws InterruptedException {
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    Coupon coupon = couponRepository.findByCouponName("DEWDEW_001");
                    // 분산락 적용 메서드 호출 (락의 key는 쿠폰의 name으로 설정)
                    couponService.decrease(coupon.getCouponName(), coupon.getId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        Coupon persistCoupon = couponRepository.findByCouponName("DEWDEW_001");

        assertThat(persistCoupon.getQuantity()).isZero();
        System.out.println("잔여 쿠폰 개수 = " + persistCoupon.getQuantity());
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
                    couponService.decrease(1L);
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