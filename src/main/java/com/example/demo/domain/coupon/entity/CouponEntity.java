package com.example.demo.domain.coupon.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// @AllArgsConstructor 지양하는 이유
// 필드의 순서가 바뀌면 버그가 발생
//  
// ex) private String name; 
//     private int age;
// Person person = new Person("테스트",30); 정상

// 필드의 순서가 바뀜
// private int age;
// private String name; 
// // Person person = new Person("테스트",30); 에러





@Entity
@Table(name = "tb_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 무분별한 객체 생성 방지
@ToString
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Column(nullable = false)
    private String couponName; // 쿠폰 이름 (예: 10% 할인 쿠폰)

    @Column(nullable = false)
    private Double discountRate; // 할인율

    @Column(nullable = false)
    private Integer totalQuantity; // 총 수량

    @Column(nullable = false)
    private Integer currentQuantity; // 현재 남은 수량 (락의 핵심 대상)

    private LocalDateTime startDate; // 시작일
    private LocalDateTime endDate;   // 종료일

    // 빌더를 클래스가 아닌 이 생성자에 붙입니다.
    @Builder
    public CouponEntity(String couponName, Double discountRate, Integer totalQuantity,   
    			Integer currentQuantity, LocalDateTime startDate, LocalDateTime endDate) {
        this.couponName = couponName;
        this.discountRate = discountRate;
        this.totalQuantity = totalQuantity;
        this.currentQuantity = currentQuantity; 
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * 쿠폰 수량 차감
     */
    public void decreaseQuantity() {
        if (this.currentQuantity <= 0) {
            throw new RuntimeException("남은 쿠폰 수량이 없습니다. (ID: " + this.id + ")");
        }
        this.currentQuantity -= 1;
    }

    /**
     * 유효 기간 확인
     */
    public boolean isValidDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(startDate) && now.isBefore(endDate);
    }
}
