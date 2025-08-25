package com.prasanth.coupon_manage.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prasanth.coupon_manage.dto.ApplicableCouponResponse;
import com.prasanth.coupon_manage.dto.ApplyCouponResponse;
import com.prasanth.coupon_manage.dto.CartRequest;
import com.prasanth.coupon_manage.dto.CartRequest.CartItemRequest;
import com.prasanth.coupon_manage.dto.CouponRequest.ProductQuantity;
import com.prasanth.coupon_manage.models.Coupon;
import com.prasanth.coupon_manage.repositories.CouponRepository;
import com.prasanth.coupon_manage.services.intrfac.ICouponApplicationService;

@Service
public class CouponApplicationService implements ICouponApplicationService {

    @Autowired
    private CouponRepository couponRepository;



    @Override
    public List<ApplicableCouponResponse.CouponDiscount> getApplicableCoupons(CartRequest cartRequest){
        List<ApplicableCouponResponse.CouponDiscount> applicableCoupons = new ArrayList<>();
        List<Coupon> coupons = couponRepository.findAll();
        double cartTotal = cartRequest.getItems().stream()
        .mapToDouble(item -> item.getPrice() * item.getQuantity())
        .sum();

        for(Coupon coupon : coupons){
            Double discount = 0.0;
            switch(coupon.getType()){
                case CART_WISE:
                    if(cartTotal >= coupon.getThreshold()){
                        discount = (cartTotal * coupon.getDiscount())/100.0;
                        
                    }
                    break;
                case PRODUCT_WISE:
                    for(CartRequest.CartItemRequest item :cartRequest.getItems()){
                        if(coupon.getProduct() !=null && item.getProductId().equals(coupon.getProduct().getId())){
                            discount = (item.getPrice() * item.getQuantity() * coupon.getDiscount())/100.0;
                            break;
                        }
                    }
                    break;
                case BXGY:
                    try{
                        ObjectMapper mapper=new ObjectMapper();
                        List<ProductQuantity> buyProducts= mapper.readValue(coupon.getBuyProducts(), new TypeReference<List<ProductQuantity>>() {});
                        List<ProductQuantity> getProducts= mapper.readValue(coupon.getGetProducts(), new TypeReference<List<ProductQuantity>>() {});

                        int repetition =Integer.MAX_VALUE;
                        for(ProductQuantity buy: buyProducts){
                            int cartQty= cartRequest.getItems().stream()
                                                    .filter(i -> i.getProductId().equals(buy.getProductId()))
                                                    .mapToInt(CartItemRequest::getQuantity)
                                                    .sum();
                            int possible= cartQty/buy.getQuantity();
                            repetition =Math.min(repetition,possible);
                        }
                        repetition = Math.min(repetition,coupon.getRepeitionLimit()!=null ?coupon.getRepeitionLimit():repetition);

                        for(ProductQuantity get: getProducts){
                            double price = cartRequest.getItems().stream()
                                                    .filter(i->i.getProductId().equals(get.getProductId()))
                                                    .mapToDouble(CartItemRequest::getPrice)
                                                    .findFirst()
                                                    .orElse(0.0);
                            discount +=price * get.getQuantity() * repetition;
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
            if(discount>0){
                ApplicableCouponResponse.CouponDiscount couponDiscount=new ApplicableCouponResponse.CouponDiscount();
                couponDiscount.setCouponId(coupon.getId());
                couponDiscount.setType(coupon.getType());
                couponDiscount.setDiscount(discount);
                applicableCoupons.add(couponDiscount);
            }

        }


        return applicableCoupons;
        
    }

    @Override
    public ApplyCouponResponse applyCouponToCart(Long couponId, CartRequest cartRequest) {
        ApplyCouponResponse response = new ApplyCouponResponse();
        ApplyCouponResponse.UpdatedCart updatedCart = new ApplyCouponResponse.UpdatedCart();
        List<ApplyCouponResponse.DiscountedItem> discountedItems = new ArrayList<>();

        Coupon coupon = couponRepository.findById(couponId).orElse(null);
        if (coupon == null) return response;

        double totalPrice = 0.0;
        double totalDiscount = 0.0;

        List<CartRequest.CartItemRequest> items = cartRequest.getItems();

        switch (coupon.getType()) {
            case CART_WISE:
                totalPrice = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
                if (totalPrice >= coupon.getThreshold()) {
                    totalDiscount = (totalPrice * coupon.getDiscount()) / 100.0;
                }
                for (CartRequest.CartItemRequest item : items) {
                    ApplyCouponResponse.DiscountedItem di = new ApplyCouponResponse.DiscountedItem();
                    di.setProductId(item.getProductId());
                    di.setQuantity(item.getQuantity());
                    di.setPrice(item.getPrice());
                    di.setTotalDiscount(0.0);
                    discountedItems.add(di);
                }
                break;

            case PRODUCT_WISE:
                for (CartRequest.CartItemRequest item : items) {
                    ApplyCouponResponse.DiscountedItem di = new ApplyCouponResponse.DiscountedItem();
                    di.setProductId(item.getProductId());
                    di.setQuantity(item.getQuantity());
                    di.setPrice(item.getPrice());
                    if (coupon.getProduct() != null && item.getProductId().equals(coupon.getProduct().getId())) {
                        double discount = (item.getPrice() * item.getQuantity() * coupon.getDiscount()) / 100.0;
                        di.setTotalDiscount(discount);
                        totalDiscount += discount;
                    } else {
                        di.setTotalDiscount(0.0);
                    }
                    discountedItems.add(di);
                    totalPrice += item.getPrice() * item.getQuantity();
                }
                break;

            case BXGY:
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    List<ProductQuantity> buyProducts = mapper.readValue(coupon.getBuyProducts(), new TypeReference<List<ProductQuantity>>() {});
                    List<ProductQuantity> getProducts = mapper.readValue(coupon.getGetProducts(), new TypeReference<List<ProductQuantity>>() {});

                    int repetition = Integer.MAX_VALUE;
                    for (ProductQuantity buy : buyProducts) {
                        int cartQty = items.stream()
                            .filter(i -> i.getProductId().equals(buy.getProductId()))
                            .mapToInt(CartItemRequest::getQuantity)
                            .sum();
                        int possible = cartQty / buy.getQuantity();
                        repetition = Math.min(repetition, possible);
                    }
                    repetition = Math.min(repetition, coupon.getRepeitionLimit() != null ? coupon.getRepeitionLimit() : repetition);

                    for (CartRequest.CartItemRequest item : items) {
                        ApplyCouponResponse.DiscountedItem di = new ApplyCouponResponse.DiscountedItem();
                        di.setProductId(item.getProductId());
                        di.setQuantity(item.getQuantity());
                        di.setPrice(item.getPrice());
                        di.setTotalDiscount(0.0);
                        discountedItems.add(di);
                        totalPrice += item.getPrice() * item.getQuantity();
                    }

                    for (ProductQuantity get : getProducts) {
                        double price = items.stream()
                            .filter(i -> i.getProductId().equals(get.getProductId()))
                            .mapToDouble(CartItemRequest::getPrice)
                            .findFirst()
                            .orElse(0.0);
                        int freeQty = get.getQuantity() * repetition;
                        if (freeQty > 0) {
                            ApplyCouponResponse.DiscountedItem di = new ApplyCouponResponse.DiscountedItem();
                            di.setProductId(get.getProductId());
                            di.setQuantity(freeQty);
                            di.setPrice(price);
                            double discount = price * freeQty;
                            di.setTotalDiscount(discount);
                            discountedItems.add(di);
                            totalDiscount += discount;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        double finalPrice = totalPrice - totalDiscount;

        updatedCart.setItems(discountedItems);
        updatedCart.setTotalPrice(totalPrice);
        updatedCart.setTotalDiscount(totalDiscount);
        updatedCart.setFinalPrice(finalPrice);

        response.setUpdatedCart(updatedCart);
        return response;
    }

}
