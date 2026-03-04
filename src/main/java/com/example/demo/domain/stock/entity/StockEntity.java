package com.example.demo.domain.stock.entity;

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

@Table(name = "TB_STOCK")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stockId")
    private Long stockId;

    private Long productId; // 상품과의 연관관계 (FK 역할)

    private Integer quantity; // 현재 재고 수량

    @Builder
    public StockEntity(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // 재고 감소 로직 (DB 반영용)
    public void decrease(int quantity) {
        if (this.quantity < quantity) {
            throw new RuntimeException("재고가 부족합니다.");
        }
        this.quantity -= quantity;
    }
}
