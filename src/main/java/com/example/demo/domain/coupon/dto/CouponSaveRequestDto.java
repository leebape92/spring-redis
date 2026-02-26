package com.example.demo.domain.coupon.dto;

import java.time.LocalDateTime;

import com.example.demo.domain.coupon.entity.CouponEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JSON 역직렬화를 위해 필요
public class CouponSaveRequestDto {
	
    private String couponName;
    private Double discountRate;
    private Integer totalQuantity;
    private Integer currentQuantity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // 
    @Builder
    public CouponSaveRequestDto(String couponName, Double discountRate, Integer totalQuantity,   
    			Integer currentQuantity, LocalDateTime startDate, LocalDateTime endDate) {
        this.couponName = couponName;
        this.discountRate = discountRate;
        this.totalQuantity = totalQuantity;
        this.currentQuantity = currentQuantity; 
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    /**
     * Entity 변환 로직
     */
    // this 메서드를 호출하고있는 객체 자신을 가르킴
    public CouponEntity toCouponEntity() {
    	// 빌더 결과를 변수에 할당
        CouponEntity couponEntity = CouponEntity.builder()
                .couponName(this.couponName)
                .discountRate(this.discountRate)
                .totalQuantity(this.totalQuantity)
                .currentQuantity(this.currentQuantity)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
        
        System.out.println("couponEntity ::: " + couponEntity.toString());

        // 변수 반환
        return couponEntity;
    }
}
