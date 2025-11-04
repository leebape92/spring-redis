package com.example.demo.reservation.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_reservation")
public class ReservationEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예약 고유 ID

    @Column(nullable = false)
    private Long userId; // 예약자 (FK)

    @Column(nullable = false)
    private Long itemId; // 예약 대상 (상품, 서비스 등 FK)

    @Column(nullable = false)
    private LocalDateTime reservationTime; // 실제 예약 시간

    @Column(nullable = false)
    private LocalDateTime createdAt; // 예약 생성 시각

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 예약 수정 시각

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private String status; // 예약 상태 (예: PENDING, CONFIRMED, CANCELED)

    @Column(length = 500)
    private String memo; // 비고 또는 요청사항
	
}
