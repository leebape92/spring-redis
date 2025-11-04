package com.example.demo.order.entity;

import java.time.LocalDateTime;

import com.example.demo.product.entity.ProductEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "TB_ORDER")
@Entity
@Data // getter, setter, toString, equals, hashCode 자동 생성
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 포함하는 생성자
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @Column(name = "order_status", nullable = false, length = 20)
    private String orderStatus;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    public void cancel() {
        this.orderStatus = "CANCELLED";
        productEntity.increaseStock(this.quantity); // 재고 복원
    }
}
