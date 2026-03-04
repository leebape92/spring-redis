package com.example.demo.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.order.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
