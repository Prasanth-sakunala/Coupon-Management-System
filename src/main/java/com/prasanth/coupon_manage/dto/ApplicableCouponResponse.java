package com.prasanth.coupon_manage.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prasanth.coupon_manage.models.CouponType;

import lombok.Data;

@Data
public class ApplicableCouponResponse {
    private List<CouponDiscount> applicableCoupons;

    @Data
    public static class CouponDiscount{
        @JsonProperty("coupon_id")
        private Long couponId;
        private CouponType type;
        private Double discount;
    }

}
