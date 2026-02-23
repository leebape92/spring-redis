package com.example.demo.domain.coupon.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 무분별한 객체 생성 방지
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 쿠폰 이름 (예: 10% 할인 쿠폰)

    @Column(nullable = false)
    private Double discountRate; // 할인율

    @Column(nullable = false)
    private Integer totalQuantity; // 총 발행 수량

    @Column(nullable = false)
    private Integer currentQuantity; // 현재 남은 수량 (락의 핵심 대상)

    private LocalDateTime startDate; // 시작일
    private LocalDateTime endDate;   // 종료일

    // --- 비즈니스 로직 ---

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
