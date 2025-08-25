package com.prasanth.coupon_manage.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CartRequest {
    private List<CartItemRequest> items;

    @Data
    public static class CartItemRequest{
        @JsonProperty("product_id")
        private Long productId; 
        private Integer quantity;
        private Double price;
    }

}
