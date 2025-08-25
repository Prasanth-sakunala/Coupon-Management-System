# Coupon Management System 🏷️

## 📌 Overview

A Spring Boot–based backend application for managing and applying coupons in an e‑commerce environment.  
Supports cart‑wide, product‑specific, and Buy X Get Y (BXGY) coupons, with a clean, extensible architecture and well‑structured DTOs for maintainability.

---

## ✅ Implemented Features

### 1. Cart‑Wise Coupon
Applies a percentage discount to the entire cart if the total exceeds a given threshold.

**Example:**
```json
{
  "type": "cart-wise",
  "details": { "threshold": 100, "discount": 10 }
}
```

---

### 2. Product‑Wise Coupon
Applies a percentage discount to a specific product in the cart.

**Example:**
```json
{
  "type": "product-wise",
  "details": { "product_id": 1, "discount": 20 }
}
```

---

### 3. BXGY Coupon
Provides free products when certain quantities of other products are purchased, with a repetition limit.

**Example:**
```json
{
  "type": "bxgy",
  "details": {
    "buy_products": [
      { "product_id": 1, "quantity": 3 },
      { "product_id": 2, "quantity": 3 }
    ],
    "get_products": [
      { "product_id": 3, "quantity": 1 }
    ],
    "repition_limit": 2
  }
}
```

---

### 4. Coupon CRUD Operations
Full REST API for creating, updating, retrieving, and deleting coupons.

---

### 5. Applicable Coupons Calculation
`POST /applicable-coupons` — returns all coupons applicable to a cart with their calculated discounts.

---

### 6. Apply Coupon to Cart
`POST /apply-coupon/{id}` — applies a specific coupon to the cart and returns the updated prices.

---

## 💡 Potential Future Enhancements

- Coupon validity period (start/end dates)
- Usage limits (per user / per coupon / global)
- Minimum and maximum discount limits
- User‑specific or targeted promotions
- Coupon codes (manual entry)
- Coupon status (active/inactive/expired)
- Shipping‑fee discounts
- Fixed‑amount discounts (currently only percentage supported)

---

## 🔗 API Endpoints

### 📄 Coupon Endpoints
- `POST /coupons` — Create a new coupon
- `GET /coupons` — Retrieve all coupons
- `GET /coupons/{id}` — Retrieve coupon by ID
- `PUT /coupons/{id}` — Update coupon by ID
- `DELETE /coupons/{id}` — Delete coupon by ID

### 🛒 Coupon Application Endpoints
- `POST /applicable-coupons` — Get applicable coupons for a cart
- `POST /apply-coupon/{id}` — Apply a specific coupon to a cart

---

## 📂 Code Structure

- **Controllers:** [CouponController.java](src/main/java/com/prasanth/coupon_manage/controller/CouponController.java), [CouponApplicationController.java](src/main/java/com/prasanth/coupon_manage/controller/CouponApplicationController.java)
- **Services:** [CouponService.java](src/main/java/com/prasanth/coupon_manage/services/CouponService.java), [CouponApplicationService.java](src/main/java/com/prasanth/coupon_manage/services/CouponApplicationService.java)
- **Models:** [Coupon.java](src/main/java/com/prasanth/coupon_manage/models/Coupon.java), [Product.java](src/main/java/com/prasanth/coupon_manage/models/Product.java), [CouponType.java](src/main/java/com/prasanth/coupon_manage/models/CouponType.java)
- **DTOs:** [ApplicableCouponResponse.java](src/main/java/com/prasanth/coupon_manage/dto/ApplicableCouponResponse.java), [ApplyCouponResponse.java](src/main/java/com/prasanth/coupon_manage/dto/ApplyCouponResponse.java), [CouponResponse.java](src/main/java/com/prasanth/coupon_manage/dto/CouponResponse.java), [CouponRequest.java](src/main/java/com/prasanth/coupon_manage/dto/CouponRequest.java), [CartRequest.java](src/main/java/com/prasanth/coupon_manage/dto/CartRequest.java)

---

## 🚀 How to Run

- Configure DB in `application.properties`
- Build & run with Maven:
  ```sh
  ./mvnw spring-boot:run
  ```
- Access API endpoints as per the documentation above

---

## 📌 Notes

- Only three coupon types currently implemented
- Percentage‑based discounts only
- Detailed test cases and example payloads included