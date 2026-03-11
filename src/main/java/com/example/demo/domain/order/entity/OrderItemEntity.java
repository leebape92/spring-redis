package com.example.demo.domain.order.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_ORDER_ITEM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer orderQuantity;

    @Column(nullable = false)
    private BigDecimal orderPrice; // 주문 당시의 상품 단가

    @Builder
    private OrderItemEntity(Long productId, Integer orderQuantity, BigDecimal orderPrice) {
        this.productId = productId;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderPrice;
    }

    // 해당 품목의 총액 (단가 * 수량)
    public BigDecimal getTotalItemPrice() {
        return orderPrice.multiply(new BigDecimal(orderQuantity));
    }

 // 외부 노출 최소화
    protected void assignOrder(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }
}
