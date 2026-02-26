package com.example.demo.domain.coupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.coupon.dto.CouponIssueRequestDto;
import com.example.demo.domain.coupon.dto.CouponSaveRequestDto;
import com.example.demo.domain.coupon.service.CouponIssueFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

    @Autowired
    private CouponIssueFacade couponIssueFacade;
    
//    @PostMapping("/save")
//    public ResponseEntity<Void> couponSave(@RequestBody CouponSaveRequestDto couponSaveRequestDto) {
//        // Facade를 호출하여 락과 트랜잭션을 한꺼번에 처리
//        couponIssueFacade.couponSave(couponSaveRequestDto);
//        return ResponseEntity.ok().build();
//    }
    
    @PostMapping("/issue")
    public ResponseEntity<Void> couponIssue(@RequestBody CouponIssueRequestDto couponIssueRequestDto) {
        // Facade를 호출하여 락과 트랜잭션을 한꺼번에 처리
        couponIssueFacade.couponIssue(couponIssueRequestDto);
        return ResponseEntity.ok().build();
    }
    
	
}
