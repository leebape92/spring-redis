package com.example.demo.domain.product.entity;

import java.math.BigDecimal;
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

// 접근 권한: protected는 같은 패키지이거나 상속 관계일 때만 호출할 수 있습니다.
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
// JPA는 데이터베이스의 값을 꺼내 자바 객체로 변환
// DB에서 데이터를 꺼내서 Product 객체를 만들려는데, **아무 인자도 없는 생성자(기본 생성자)**가 없어서 객체를 못 만들겠어!"

// JPA는 기본 생성자가 필요하지만 개발자가 빈 객체를 만드는 것은 막아야함

@Entity
@Table(name = "TB_PRODUCT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private BigDecimal productPrice;

    @Column(name = "totalQuantity", nullable = false) //총 수량
    private Integer totalQuantity;
    
    @Column(name = "currentQuantity", nullable = false)
    private Integer currentQuantity; //현재 수량

    @Column(length = 20)
    private String status; // 판매중, 품절 등

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 기본 생성자
    @Builder
    public ProductEntity(String productName, String description, BigDecimal productPrice, 
                         int totalQuantity, int currentQuantity, String status) {
        this.productName = productName;
        this.description = description;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.currentQuantity = currentQuantity;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    

//    // === 비즈니스 로직 ===
//    public void decreaseStock(int quantity) {
//        if (this.stockQuantity < quantity) {
//            throw new IllegalStateException("재고가 부족합니다. 현재 재고: " + this.stockQuantity);
//        }
//        this.stockQuantity -= quantity;
//    }
//
//    public void increaseStock(int quantity) {
//        this.stockQuantity += quantity;
//    }
}
