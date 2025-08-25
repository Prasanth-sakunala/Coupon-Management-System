package com.prasanth.coupon_manage.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "coupons")
public class Coupon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType type;

    private Integer threshold;
    private Integer discount;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = true)
    private Product product;
    
    private Integer repeitionLimit;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String buyProducts;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String getProducts; 


}
