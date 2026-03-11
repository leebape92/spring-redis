//package com.example.demo.domain.order.entity;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "TB_ORDER") // order는 SQL 예약어인 경우가 많아 s를 붙이거나 명시하는 게 안전합니다.
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class OrderEntity_back {
//
//	// tinyint 와 int
//	// tinyint : 상태값, 코드
//	// int : 수량,가격 
//	
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "orderId")
//    private Long orderId;
//
//    @Column(nullable = false)
//    private Long productId;
//
//    @Column(nullable = false, columnDefinition = " INT COMMENT '주문수량'")
//    private Integer orderQuantity;
//
//    @Column(nullable = false)
//    private BigDecimal totalPrice;
//
//    @Column(nullable = false, columnDefinition = "TINYINT COMMENT '10:주문, 20:취소, 30:완료'")
//    private Integer status; // 숫자 그대로 저장 (예: 10, 20)
//
//    private LocalDateTime orderTime;
//
//    @Builder
//    private OrderEntity_back(Long productId, Integer orderQuantity, BigDecimal totalPrice, Integer status) {
//        this.productId = productId;
//        this.orderQuantity = orderQuantity;
//        this.totalPrice = totalPrice;
//        this.status = status;
//        this.orderTime = LocalDateTime.now();
//    }
//    
//}