package com.example.scenario.service;

import com.example.scenario.entity.Coupon;
import com.example.scenario.repository.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("test")
class MultiServerCouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;
//    @Test
//    void decrease_with_100_request_to_multi_server() throws InterruptedException {
//        // given
//        int threadCount = 100;
//        RestTemplate restTemplate = new RestTemplate();
//        ExecutorService executorService = Executors.newFixedThreadPool(32);
//        CountDownLatch latch = new CountDownLatch(threadCount);
//
//        // when
//        for (int i = 0; i < threadCount; i++) {
//            final int ii = i;
//            executorService.submit(() -> {
//                try {
//                    int port = (ii % 2 == 0) ? 8080 : 8081;
//                    ResponseEntity<Void> forEntity = restTemplate.getForEntity(
//                        "http://localhost:" + port + "/stocks/1/decrease",
//                        Void.class);
//                }  finally {
//                    latch.countDown();
//                }
//            });
//        }
//        latch.await();
//    }

    @Test
    @DisplayName("쿠폰 발급 테스트 (멀티 스레드)")
    void couponIssuanceForMultiThreadTest() throws InterruptedException {
        Coupon coupon = new Coupon(1L, "abc", 10L);

        AtomicInteger successCount = new AtomicInteger();
        int numberOfExecute = 100;
        ExecutorService service = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(numberOfExecute);
        couponRepository.saveAndFlush(coupon);
//        Event event = Event.of(requestDto, LocalDateTime.now(), mockCoupon);
//        when(eventRepository.findByEventIdForUpdate(anyLong())).thenReturn(Optional.ofNullable(event));

        for (int i = 0; i < numberOfExecute; i++) {
            final int threadNumber = i + 1;
            service.submit(() -> {
                try {
                    couponService.decrease(1L,1L);
                    successCount.getAndIncrement();
                    System.out.println("Thread " + threadNumber + " - 성공");
                } catch (PessimisticLockingFailureException e) {
                    System.out.println("Thread " + threadNumber + " - 락 충돌 감지");
                } catch (Exception e) {
                    System.out.println("Thread " + threadNumber + " - " + e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();

        // 성공한 경우의 수가 10개라고 가정.
//        assertThat(successCount.get()).isEqualTo(10);
        assertEquals(10L, successCount.get());
    }
}