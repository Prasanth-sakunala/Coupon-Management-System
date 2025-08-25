package com.prasanth.coupon_manage.services.intrfac;

import java.util.List;

import com.prasanth.coupon_manage.dto.CouponRequest;
import com.prasanth.coupon_manage.dto.CouponResponse;

public interface ICouponService {

    CouponResponse createCoupon(CouponRequest request);
    CouponResponse getCouponById(Long couponId);
    List<CouponResponse> getAllCoupons();
    CouponResponse updateCoupon(Long couponId,CouponRequest request);
    String deleteCoupon(Long couponId);
    
    
    

}
