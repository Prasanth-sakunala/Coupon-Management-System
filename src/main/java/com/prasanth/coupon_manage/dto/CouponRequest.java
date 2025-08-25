package com.prasanth.coupon_manage.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prasanth.coupon_manage.models.CouponType;

import lombok.Data;

@Data
public class CouponRequest {
    private CouponType type;
    private Details details;

    @Data
    public static class Details{
        private Integer threshold;
        private Integer discount;
        @JsonProperty("product_id")
        private Long productId;
        private Integer repeitionLimit;
        @JsonProperty("buy_products")
        private List<ProductQuantity> buyProducts;
        @JsonProperty("get_products")
        private List<ProductQuantity> getProducts;
    }

    @Data
    public static class ProductQuantity{
        @JsonProperty("product_id")
        private Long productId;
        private Integer quantity;
    }

}
