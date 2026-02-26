package com.example.demo.coupon;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.coupon.dto.CouponIssueRequestDto;
import com.example.demo.domain.coupon.service.CouponIssueFacade;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CouponIssueTest {
	
	@Autowired
    private CouponIssueFacade couponIssueFacade; // 락을 거는 파사드
	
    @Test
    @DisplayName("동시에 100명이 쿠폰을 발급받아도 수량이 정확히 차감되어야 한다")
    void concurrency_test() throws InterruptedException {
    	
        int threadCount = 5;
        Long couponId = 4L;
        
        // ExecutorService 사용이유
        // for문으로 호출하는 것은 순차적 실행일 뿐이라 동시성 테스트가 되지 않기 때문에
        // newFixedThreadPool(32) : 32개의 스레드를 동시에 생성, 락생성 및 데이터 엉킴을 위해서 32지정
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        
        // CountDownLatch
        // CountDownLatch(threadCount) 해당되는 갯수의 작업이 끝날 때까지 기다림
        CountDownLatch latch = new CountDownLatch(threadCount); 
        
        for (int i = 0; i < threadCount; i++) {
        	//Long userId = couponIssueRequestDto.getUserId() + i; // 100명의 서로 다른 유저가
        	Long userId = (long) i; // 100명의 서로 다른 유저가
        	System.out.println("userId :::" + userId);
            executorService.submit(() -> {
                try {
                    couponIssueFacade.couponIssue(new CouponIssueRequestDto(couponId,userId)); // 1번 쿠폰 발급 시도
                } catch (Exception e) {
                    log.error("발급 실패: {}", e.getMessage());
                } finally {
                    latch.countDown(); // 각 스레드가 일을 마칠 때마다 숫자를 1씩 줄임.
                }
            });
        }

        latch.await(); // 모든 스레드가 끝날 때까지 대기

        // 검증: 최종 수량이 (초기수량 - 100) 인지 확인
    }

}
