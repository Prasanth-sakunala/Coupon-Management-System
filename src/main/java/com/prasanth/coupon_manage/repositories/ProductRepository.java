package com.prasanth.coupon_manage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasanth.coupon_manage.models.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

}
