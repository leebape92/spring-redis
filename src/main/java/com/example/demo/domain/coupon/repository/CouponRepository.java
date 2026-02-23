package com.example.demo.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.coupon.entity.CouponEntity;

public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

}
