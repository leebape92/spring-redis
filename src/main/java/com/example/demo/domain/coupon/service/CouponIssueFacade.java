package com.example.demo.domain.coupon.service;

import org.springframework.stereotype.Component;

import com.example.demo.domain.coupon.dto.CouponIssueRequestDto;
import com.example.demo.global.annotation.DistributedLock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponIssueFacade {
	
	// 클래스 사용이유
	// 락 시작 -> 트랜잭션 시작 -> 트랜잭션 종료(DB반영) -> 락 해제 순서를 100% 보장할 수 있습니다.
	// 
	
    private final CouponService couponService;

    @DistributedLock(
    		keyPrefix = "COUPON",
    		key = "#couponIssueRequestDto.couponId",
    		waitTime = 5,
    		leaseTime = 3
    ) // 1. 락획득 DistributedLockAspect.java
    public void couponIssue(CouponIssueRequestDto couponIssueRequestDto) {
    	log.info("발급 요청 데이터: {}", couponIssueRequestDto);
        // 2. 문이 잠긴 안전한 상태에서 트랜잭션을 시작합니다.
        couponService.couponIssue(couponIssueRequestDto.getCouponId(),couponIssueRequestDto.getUserId()); 
        // 4. 트랜잭션이 완전히 종료(Commit)되어 DB 반영이 끝난 후 이 메서드를 나갑니다.
    }
} // 5. 여기서 대문 잠금을 해제합니다. 다음 사람이 들어왔을 땐 이미 DB에 숫자가 줄어있습니다.


