package com.example.demo.domain.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.demo.domain.coupon.entity.CouponEntity;
import com.example.demo.domain.product.entity.ProductEntity;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSaveRequestDto {
	
    private String productName;
    private String description;
    private BigDecimal productPrice;
    private Integer totalQuantity;
    private Integer currentQuantity;
    private String status;

    
    // dto -> entiy 변환
    public ProductEntity toProductEntity() {
    	// 빌더 결과를 변수에 할당
    	ProductEntity productEntity = ProductEntity.builder()
                .productName(this.productName)
                .description(this.description)
                .productPrice(this.productPrice)
                .totalQuantity(this.totalQuantity)
                .currentQuantity(this.currentQuantity)
                .status(this.status)
                // createdAt, updatedAt : productEntity에서 관리
                .build();

        // 변수 반환
        return productEntity;
    }


//    “ProductDTO가 Entity로부터 생성된다”
    // Entity로 부터 PrdouctDTO 생성
//    public static ProductSaveRequestDto fromProductDrecordTOEntity(ProductEntity productEntity) {
//        return ProductSaveRequestDto.builder()
//                .id(productEntity.getId())
//                .name(productEntity.getName())
//                .description(productEntity.getDescription())
//                .price(productEntity.getPrice())
//                .stockQuantity(productEntity.getStockQuantity())
//                .status(productEntity.getStatus())
//                .createdAt(productEntity.getCreatedAt())
//                .updatedAt(productEntity.getUpdatedAt())
//                .build();
//    }
    
    
    
}

