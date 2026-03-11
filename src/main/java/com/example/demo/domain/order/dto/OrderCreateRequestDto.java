package com.example.demo.domain.order.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateRequestDto {

	// 여러 상품 정보를 담는 내부 클래스 리스트
    private List<OrderItemRequestDto> orderItemRequests; 
    private Integer status;
    
    @Builder
	public OrderCreateRequestDto(List<OrderItemRequestDto> orderItemRequests, Integer status) {
		this.orderItemRequests = orderItemRequests;
		this.status = status;
	}
}
