package com.prasanth.coupon_manage.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CouponType {
        @JsonProperty("cart-wise")
        CART_WISE,
        @JsonProperty("product-wise")
        PRODUCT_WISE,
        @JsonProperty("bxgy")
        BXGY

}
