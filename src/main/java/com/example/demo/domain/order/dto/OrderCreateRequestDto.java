package com.example.demo.domain.order.dto;

import com.example.demo.domain.order.entity.OrderEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateRequestDto {

	private Long productId;
	private Integer quantity;
	
	
	@Builder
	public OrderCreateRequestDto(Long productId, Integer quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
	

	// dto -> entity
	public OrderEntity toOrderEntity() {
		OrderEntity orderEntity = OrderEntity.builder()
				.productId(this.productId)
				.quantity(this.quantity)
				.build();
		return orderEntity;
	}



}
