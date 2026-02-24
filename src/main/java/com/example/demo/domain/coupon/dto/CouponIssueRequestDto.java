package com.example.demo.domain.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor // 기본 생성자(매개변수가 없는 생성자)를 생성합니다.
@AllArgsConstructor // 클래스의 모든 필드를 매개변수로 받는 생성자를 생성합니다.
public class CouponIssueRequestDto {
	
	Long couponId;
	Long userId;
	
}
