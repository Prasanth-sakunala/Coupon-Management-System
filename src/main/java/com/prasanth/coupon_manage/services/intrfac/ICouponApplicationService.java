package com.prasanth.coupon_manage.services.intrfac;

import java.util.List;

import com.prasanth.coupon_manage.dto.ApplicableCouponResponse;
import com.prasanth.coupon_manage.dto.ApplyCouponResponse;
import com.prasanth.coupon_manage.dto.CartRequest;

public interface ICouponApplicationService {
    List<ApplicableCouponResponse.CouponDiscount> getApplicableCoupons(CartRequest cartRequest);
    ApplyCouponResponse applyCouponToCart(Long couponId, CartRequest cartRequest);

}
