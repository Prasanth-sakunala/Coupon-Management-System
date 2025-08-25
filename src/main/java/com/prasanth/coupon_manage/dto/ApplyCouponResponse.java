package com.prasanth.coupon_manage.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApplyCouponResponse {
    @JsonProperty("updated_cart")
    private UpdatedCart updatedCart;

    @Data
    public static class UpdatedCart {
        private List<DiscountedItem> items;
        @JsonProperty("total_price")
        private Double totalPrice;
        @JsonProperty("total_discount")
        private Double totalDiscount;
        @JsonProperty("final_price")
        private Double finalPrice;
    }

    @Data
    public static class DiscountedItem {
        @JsonProperty("product_id")
        private Long productId;
        private Integer quantity;
        private Double price;
        @JsonProperty("total_discount")
        private Double totalDiscount;
    }

}
