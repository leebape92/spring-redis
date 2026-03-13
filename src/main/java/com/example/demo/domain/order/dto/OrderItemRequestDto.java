package com.example.demo.domain.order.dto;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class OrderItemRequestDto {
    private Long productId;
    private Integer orderQuantity;
    private BigDecimal orderPrice;
    
}
