package com.example.demo.domain.coupon.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.coupon.entity.CouponEntity;
import com.example.demo.domain.coupon.repository.CouponRepository;
import com.example.demo.global.annotation.DistributedLock;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    
    @Transactional
    @DistributedLock(key = "#couponId", waitTime = 5, leaseTime = 3)
    public void issueCoupon(Long couponId, Long userId) {
    	// 1. DB에서 엔티티 조회
        CouponEntity coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException("쿠폰 없음"));

        // 2. 엔티티 내부 로직 실행 (락 덕분에 동시성 걱정 없음)
        coupon.decreaseQuantity();
        
        // 3. 변경 감지(Dirty Checking)로 인해 트랜잭션 종료 시 DB에 자동 반영
    }
}
