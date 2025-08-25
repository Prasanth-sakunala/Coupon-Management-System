package com.prasanth.coupon_manage.dto;

import java.util.List;

import com.prasanth.coupon_manage.models.CouponType;

import lombok.Data;

@Data
public class CouponResponse {
    private Long couponid;
    private String message;
    private int statusCode;
    private CouponType type;
    private Integer threshold;
    private Integer discount;
    private Long productId;
    private Integer repeitionLimit;
    private List<CouponRequest.ProductQuantity> buyProducts;
    private List<CouponRequest.ProductQuantity> getProducts;

}
