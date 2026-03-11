package com.example.demo.domain.order.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_ORDER") // order는 SQL 예약어인 경우가 많아 s를 붙이거나 명시하는 게 안전합니다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    // mappedBy는 연관관계의 주인인 OrderItemEntity의 필드명과 일치시켜야 함
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> OrderItemEntityList = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal totalPrice; // 총 결제 금액

    @Column(nullable = false, columnDefinition = "TINYINT COMMENT '10:주문, 20:취소, 30:완료'")
    private Integer status;

    private LocalDateTime orderTime;

    @Builder
    private OrderEntity(Integer status) {
        this.status = status;
        this.totalPrice = BigDecimal.ZERO; // 초기값 0
        this.orderTime = LocalDateTime.now();
    }

    // == 비즈니스 로직: 주문 상품 추가 및 총액 계산 ==
    public void addOrderItem(OrderItemEntity orderItemEntity) {
    	// 1. 메모리 상의 리스트에 추가 (Order 객체가 Item을 알게 됨)
    	OrderItemEntityList.add(orderItemEntity);
    	
    	// 2. Item 객체에게도 주인(Order)이 누구인지 알려줌 (양방향 연결)
        // 이 작업이 있어야 DB의 TB_ORDER_ITEM.order_id 외래키가 채워집니다!
        orderItemEntity.assignOrder(this);
        
        // 3. 주문 총액 합산 (Order 객체의 상태 업데이트)
        this.totalPrice = this.totalPrice.add(orderItemEntity.getTotalItemPrice());
    }
}