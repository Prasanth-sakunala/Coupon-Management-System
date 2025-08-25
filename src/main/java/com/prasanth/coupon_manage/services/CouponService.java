package com.prasanth.coupon_manage.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prasanth.coupon_manage.Exception.OurException;
import com.prasanth.coupon_manage.dto.CouponRequest;
import com.prasanth.coupon_manage.dto.CouponResponse;
import com.prasanth.coupon_manage.models.Coupon;
import com.prasanth.coupon_manage.models.CouponType;
import com.prasanth.coupon_manage.models.Product;
import com.prasanth.coupon_manage.repositories.CouponRepository;
import com.prasanth.coupon_manage.repositories.ProductRepository;
import com.prasanth.coupon_manage.services.intrfac.ICouponService;
import com.prasanth.coupon_manage.utils.Util;

@Service
public class CouponService implements ICouponService{
    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public CouponResponse createCoupon(CouponRequest request){
        CouponResponse response =new CouponResponse();
        Coupon coupon=new Coupon();
        try{
                if(request.getType()==CouponType.CART_WISE){
                    coupon.setType(request.getType());
                    coupon.setThreshold(request.getDetails().getThreshold());
                    coupon.setDiscount(request.getDetails().getDiscount());
                    couponRepository.save(coupon);
                    // setting response
                    response=Util.setCouponResponseByType(coupon);
                    response.setMessage("cart-wise coupon created successfully");
        
                }
                else if(request.getType()==CouponType.PRODUCT_WISE){
                        coupon.setType(request.getType());
                        Long productId = request.getDetails().getProductId();
                        Product product = productRepository.findById(productId)
                            .orElseGet(() -> {
                                Product newProduct = new Product();
                                newProduct.setId(productId);
                                return productRepository.save(newProduct);
                            });
                        coupon.setProduct(product);
                        coupon.setDiscount(request.getDetails().getDiscount());
                        couponRepository.save(coupon);
                    
                    // setting response
                    response=Util.setCouponResponseByType(coupon);
                    response.setMessage("product-wise coupon created successfully");
        
                }
                else if(request.getType()==CouponType.BXGY){

                    coupon.setType(request.getType());
                    coupon.setRepeitionLimit(request.getDetails().getRepeitionLimit());

                    ObjectMapper mapper = new ObjectMapper();
                    String buyProductsJson = mapper.writeValueAsString(request.getDetails().getBuyProducts());
                    String getProductsJson = mapper.writeValueAsString(request.getDetails().getGetProducts());

                    coupon.setBuyProducts(buyProductsJson);
                    coupon.setGetProducts(getProductsJson);

                    request.getDetails().getBuyProducts().forEach(buyProduct -> {
                        Long productId = buyProduct.getProductId();
                        productRepository.findById(productId)
                            .orElseGet(() -> {
                                Product newProduct = new Product();
                                newProduct.setId(productId);
                                return productRepository.save(newProduct);
                            });
                    });
                    request.getDetails().getGetProducts().forEach(getProduct -> {
                        Long productId = getProduct.getProductId();
                        productRepository.findById(productId)
                            .orElseGet(() -> {
                                Product newProduct = new Product();
                                newProduct.setId(productId);
                                return productRepository.save(newProduct);
                            });
                    });

                    couponRepository.save(coupon);
                    
                    
                    // setting response
                    response=Util.setCouponResponseByType(coupon);
                    response.setMessage("bxgy coupon created successfully");
                }
                else{
                    throw new OurException("requestcoupon type is invalid");
                }
        }
        catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("internal server error");
        }
        return response;
    }

    @Override
    public CouponResponse getCouponById(Long couponId){  
        CouponResponse response =new CouponResponse();
        try{
            Coupon coupon =couponRepository.findById(couponId).orElseThrow(()->new OurException("coupon not found"));
            response=Util.setCouponResponseByType(coupon);
            response.setMessage("coupon fetched successfully");

        }
        catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("internal server error");
        }

        return response;
    }

    @Override
    public List<CouponResponse> getAllCoupons(){
        List<CouponResponse> response =couponRepository.findAll().stream().map(coupon->Util.setCouponResponseByType(coupon)).toList();
        return response;
    }

    @Override
    public CouponResponse updateCoupon(Long couponId,CouponRequest request){
        CouponResponse response =new CouponResponse();
        try{
            Coupon coupon =couponRepository.findById(couponId).orElseThrow(()->new OurException("coupon not found"));
            coupon=Util.setCouponByType(request);
            coupon.setId(couponId);
            couponRepository.save(coupon);
            response=Util.setCouponResponseByType(coupon);
            response.setMessage("coupon updated successfully");

        }
        catch(OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("internal server error");
        }
        return response;
    }

    @Override
    public String deleteCoupon(Long couponId){
        try{
            Coupon coupon =couponRepository.findById(couponId).orElseThrow(()->new OurException("coupon not found"));
            couponRepository.delete(coupon);
            return "coupon deleted successfully";

        }
        catch(OurException e){
            throw new OurException(e.getMessage());
        }
        catch(Exception e){
            throw new OurException("internal server error");
        }
        
    }

   

}
