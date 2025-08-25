package com.prasanth.coupon_manage.utils;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prasanth.coupon_manage.dto.CouponRequest;
import com.prasanth.coupon_manage.dto.CouponRequest.ProductQuantity;
import com.prasanth.coupon_manage.dto.CouponResponse;
import com.prasanth.coupon_manage.models.Coupon;
import com.prasanth.coupon_manage.models.CouponType;

public class Util {
    public static CouponResponse setCouponResponseByType(Coupon coupon){
        CouponResponse response=new CouponResponse();
        if(coupon.getType()==CouponType.CART_WISE){
            response.setThreshold(coupon.getThreshold());
            response.setDiscount(coupon.getDiscount());
            response.setType(coupon.getType());
            response.setCouponid(coupon.getId());
            response.setStatusCode(200);
        }
        else if(coupon.getType()==CouponType.PRODUCT_WISE){
            response.setCouponid(coupon.getId());
            response.setType(coupon.getType());
            response.setDiscount(coupon.getDiscount());
            if (coupon.getProduct() != null) {
                response.setProductId(coupon.getProduct().getId());
            }
            response.setStatusCode(201);
        }
        else if(coupon.getType()==CouponType.BXGY){
            try{
                response.setCouponid(coupon.getId());
                response.setType(coupon.getType());
                ObjectMapper mapper = new ObjectMapper();
                List<ProductQuantity> buyProducts = mapper.readValue(
                    coupon.getBuyProducts(),new TypeReference<List<ProductQuantity>>() {}
                );
                
                List<ProductQuantity> getProducts = mapper.readValue(
                    coupon.getGetProducts(),new TypeReference<List<ProductQuantity>>() {}
                );
                response.setBuyProducts(buyProducts);
                response.setGetProducts(getProducts);
                response.setRepeitionLimit(coupon.getRepeitionLimit());
                response.setStatusCode(200);
            }
            catch(Exception e){
                e.printStackTrace();
                response.setStatusCode(500);
                response.setMessage("Internal server error");
                return response;
            }
        }
        return response;
    }
    

    public static Coupon setCouponByType(CouponRequest request){
        Coupon coupon=new Coupon();
        if(request.getType()==CouponType.CART_WISE){
            coupon.setType(request.getType());
            coupon.setThreshold(request.getDetails().getThreshold());
            coupon.setDiscount(request.getDetails().getDiscount());
        }
        else if(request.getType()==CouponType.PRODUCT_WISE){
            coupon.setType(request.getType());
            coupon.setDiscount(request.getDetails().getDiscount());
        }
        else if(request.getType()==CouponType.BXGY){
            coupon.setType(request.getType());
            coupon.setRepeitionLimit(request.getDetails().getRepeitionLimit());

            try{
                ObjectMapper mapper = new ObjectMapper();
                String buyProductsJson = mapper.writeValueAsString(request.getDetails().getBuyProducts());
                String getProductsJson = mapper.writeValueAsString(request.getDetails().getGetProducts());

                coupon.setBuyProducts(buyProductsJson);
                coupon.setGetProducts(getProductsJson);
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return coupon;
    }
}
