package com.example.demo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	boolean existsByEmail(String email);
}
