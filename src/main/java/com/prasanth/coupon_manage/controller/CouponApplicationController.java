package com.prasanth.coupon_manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prasanth.coupon_manage.dto.ApplicableCouponResponse;
import com.prasanth.coupon_manage.dto.ApplyCouponResponse;
import com.prasanth.coupon_manage.dto.CartWrapperRequest;
import com.prasanth.coupon_manage.services.intrfac.ICouponApplicationService;

@RestController
public class CouponApplicationController {

     @Autowired
    private ICouponApplicationService couponApplicationService;

    @PostMapping("/applicable-coupons")
    public ResponseEntity<ApplicableCouponResponse> getApplicableCoupons(@RequestBody CartWrapperRequest cartWrapperRequest) {
        ApplicableCouponResponse response = new ApplicableCouponResponse();
        response.setApplicableCoupons(couponApplicationService.getApplicableCoupons(cartWrapperRequest.getCart()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/apply-coupon/{id}")
    public ResponseEntity<ApplyCouponResponse> applyCouponToCart(@PathVariable Long id, @RequestBody CartWrapperRequest cartWrapperRequest) {
        ApplyCouponResponse response = couponApplicationService.applyCouponToCart(id, cartWrapperRequest.getCart());
        return ResponseEntity.ok(response);
    }

}
