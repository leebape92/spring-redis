package com.example.demo.product.dto;

import java.time.LocalDateTime;

import com.example.demo.product.entity.ProductEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
	
    private Long id;
    private String name;
    private String description;
    private int price;
    private int stockQuantity;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    “ProductDTO가 Entity로부터 생성된다”
    // Entity로 부터 PrdouctDTO 생성
    public static ProductDTO fromProductDTOEntity(ProductEntity productEntity) {
        return ProductDTO.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .stockQuantity(productEntity.getStockQuantity())
                .status(productEntity.getStatus())
                .createdAt(productEntity.getCreatedAt())
                .updatedAt(productEntity.getUpdatedAt())
                .build();
    }
    
    
    
}

