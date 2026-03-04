//package com.example.demo.coupon;
//
//import java.time.LocalDateTime;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.example.demo.domain.coupon.dto.CouponSaveRequestDto;
//import com.example.demo.domain.coupon.service.CouponService;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@SpringBootTest
//public class CouponSaveTest {
//	
//	@Autowired
//    private CouponService couponService;
//
//    @Test
//    @DisplayName("쿠폰등록")
//	void CouponSave() {
//
//    	// 1. Given: 테스트에 필요한 데이터를 준비 (DTO 생성)
//        CouponSaveRequestDto couponSaveRequestDto = CouponSaveRequestDto.builder()
//        		// id는 DB가 생성하게 두어야 하므로 builder에서 제외합니다.
//                .couponName("신규 가입 10% 할인권1")
//                .discountRate(10.0)
//                .totalQuantity(100)
//                .currentQuantity(100)
//                .startDate(LocalDateTime.now())
//                .endDate(LocalDateTime.now().plusDays(30))
//                .build();
//        
//        try {
//        	couponService.saveCoupon(couponSaveRequestDto);
//        } catch (Exception e) {
//        	System.out.println("e ::: " + e);
//		}
//        
//	}
//
//}
