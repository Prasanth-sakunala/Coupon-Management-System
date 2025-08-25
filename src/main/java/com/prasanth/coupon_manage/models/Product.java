package com.prasanth.coupon_manage.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    private Long id;

    @OneToMany(mappedBy = "product")
    private List<Coupon> coupons;

}
