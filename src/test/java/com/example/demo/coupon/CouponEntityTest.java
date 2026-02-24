//package com.example.demo.coupon;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import com.example.demo.domain.coupon.entity.CouponEntity;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j // 로그를 찍기 위한 어노테이션 추가
//class CouponEntityTest {
//
//    @Test
//    @DisplayName("쿠폰 차감 성공 테스트")
//    void decrease_success() {
//        // given (쿠폰 생성)
//    	
//    	int initialQuantity = 10;
//    	
//        CouponEntity coupon = CouponEntity.builder()
//                .currentQuantity(initialQuantity)
//                .build();
//        
//        log.info("=== [Given] 초기 쿠폰 생성: 수량 {}개 ===", coupon.getCurrentQuantity());
//        
//        // when (차감)
//        coupon.decreaseQuantity();
//
//        // then (검증)
//        log.info("=== [Then] 차감 후 남은 수량: {}개 ===", coupon.getCurrentQuantity());
//        assertThat(coupon.getCurrentQuantity()).isEqualTo(initialQuantity - 1);
//    }
//
//    @Test
//    @DisplayName("수량이 0일 때 차감 실패 로그 확인")
//    void decrease_fail() {
//        // given
//        CouponEntity coupon = CouponEntity.builder()
//                .currentQuantity(0)
//                .build();
//        log.info("=== [Given] 수량 0인 쿠폰 생성 완료 ===");
//
//        // when & then
//        log.info("=== [When/Then] 예외 발생 여부 확인 시작 ===");
//        assertThatThrownBy(() -> {
//            try {
//                coupon.decreaseQuantity();
//            } catch (RuntimeException e) {
//                log.error("!!! [Expected Error] 발생한 에러 메시지: {} !!!", e.getMessage());
//                throw e; // 다시 던져줘야 assertThatThrownBy가 감지합니다.
//            }
//        }).isInstanceOf(RuntimeException.class);
//    }
//}
