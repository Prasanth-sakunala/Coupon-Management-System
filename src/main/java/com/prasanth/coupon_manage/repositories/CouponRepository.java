package com.prasanth.coupon_manage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prasanth.coupon_manage.models.Coupon;
import com.prasanth.coupon_manage.models.CouponType;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon,Long>{

    Optional<Coupon> findByType(CouponType type);

}
