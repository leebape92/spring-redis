package com.example.demo.domain.order.dto;

import java.math.BigDecimal;

import com.example.demo.domain.order.entity.OrderEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateRequestDto {

	private Long productId;
	private Integer quantity;
	private BigDecimal totalPrice;
	private Integer status;
	
	
	@Builder
	public OrderCreateRequestDto(Long productId, Integer quantity, BigDecimal totalPrice, Integer status) {
		this.productId = productId;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.status = status;
	}
	

	// dto -> entity
	public OrderEntity toOrderEntity() {
		OrderEntity orderEntity = OrderEntity.builder()
				.productId(this.productId)
				.quantity(this.quantity)
				.status(this.status)
				.build();
		return orderEntity;
	}



}
