package com.example.demo.domain.coupon.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.coupon.dto.CouponSaveRequestDto;
import com.example.demo.domain.coupon.entity.CouponEntity;
import com.example.demo.domain.coupon.repository.CouponRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    
    @Transactional
    public void saveCoupon(CouponSaveRequestDto couponSaveRequestDto) {
    	
    	CouponEntity couponEntity = couponSaveRequestDto.toCouponEntity();
    	
        couponRepository.save(couponEntity);
    }
    
    @Transactional // 2. 트랜잭션 시작
    public void couponIssue(Long couponId, Long userId) {
    	// 1. DB에서 엔티티 조회
        CouponEntity couponEntity = couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException("쿠폰 없음"));
        log.info("현재 쿠폰 수량: {}", couponEntity.getCurrentQuantity());

        // 2. 엔티티 내부 로직 실행 (락 덕분에 동시성 걱정 없음)
        couponEntity.decreaseQuantity();
        
        // 3. 변경 감지(Dirty Checking)로 인해 트랜잭션 종료 시 DB에 자동 반영
    }

}
