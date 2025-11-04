package com.example.demo.product.dto;

import java.time.LocalDateTime;

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
	
}
