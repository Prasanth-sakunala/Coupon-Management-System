# Coupon Management System

## Overview

This project is a Spring Boot-based coupon management system that supports creation, retrieval, update, deletion, and application of various coupon types to shopping carts. The system is designed to be extensible, maintainable, and efficient, with clear separation of concerns and robust DTOs.

---

## Implemented Use Cases

### 1. Cart-wise Coupon
- **Description:** Applies a percentage discount to the entire cart if the cart total exceeds a specified threshold.
- **Example:**  
  ```json
  {
    "type": "cart-wise",
    "details": {
      "threshold": 100,
      "discount": 10
    }
  }
  ```

### 2. Product-wise Coupon
- **Description:** Applies a percentage discount to a specific product in the cart.
- **Example:**  
  ```json
  {
    "type": "product-wise",
    "details": {
      "product_id": 1,
      "discount": 20
    }
  }
  ```

### 3. BXGY (Buy X Get Y) Coupon
- **Description:** Offers free products when certain quantities of other products are purchased, with a repetition limit.
- **Example:**  
  ```json
  {
    "type": "bxgy",
    "details": {
      "buy_products": [
        {"product_id": 1, "quantity": 3},
        {"product_id": 2, "quantity": 3}
      ],
      "get_products": [
        {"product_id": 3, "quantity": 1}
      ],
      "repition_limit": 2
    }
  }
  ```

### 4. Coupon CRUD Operations
- **Create, update, delete, and retrieve coupons** via REST endpoints.

### 5. Applicable Coupons Calculation
- **Endpoint:** `POST /applicable-coupons`
- **Description:** Returns all coupons applicable to a given cart and calculates the discount for each.

### 6. Apply Coupon to Cart
- **Endpoint:** `POST /apply-coupon/{id}`
- **Description:** Applies a specific coupon to the cart and returns the updated cart with discounted prices.

---

## Possible Additional Use Cases (Not Yet Implemented)

- Coupon validity period (start/end date)
- Coupon usage limits (per user, per coupon, global)
- Minimum/maximum discount limits
- User-specific coupons (targeted promotions)
- Coupon codes (manual entry)
- Coupon status (active/inactive/expired)
- Coupons for shipping or other fees
- Coupons with fixed amount discounts (currently only percentage supported)

---

## API Endpoints

### Coupon Endpoints

- `POST /coupons`  
  Create a new coupon.

- `GET /coupons`  
  Retrieve all coupons.

- `GET /coupons/{id}`  
  Retrieve a specific coupon by its ID.

- `PUT /coupons/{id}`  
  Update a specific coupon by its ID.

- `DELETE /coupons/{id}`  
  Delete a specific coupon by its ID.

### Coupon Application Endpoints

- `POST /applicable-coupons`  
  Request body:  
  ```json
  {
    "cart": {
      "items": [
        {"product_id": 1, "quantity": 6, "price": 50},
        {"product_id": 2, "quantity": 3, "price": 30},
        {"product_id": 3, "quantity": 2, "price": 25}
      ]
    }
  }
  ```
  Response:  
  ```json
  {
    "applicable_coupons": [
      {
        "coupon_id": 1,
        "type": "cart-wise",
        "discount": 40
      },
      {
        "coupon_id": 3,
        "type": "bxgy",
        "discount": 50
      }
    ]
  }
  ```

- `POST /apply-coupon/{id}`  
  Request body:  
  ```json
  {
    "cart": {
      "items": [
        {"product_id": 1, "quantity": 6, "price": 50},
        {"product_id": 2, "quantity": 3, "price": 30},
        {"product_id": 3, "quantity": 2, "price": 25}
      ]
    }
  }
  ```
  Response:  
  ```json
  {
    "updated_cart": {
      "items": [
        {"product_id": 1, "quantity": 6, "price": 50, "total_discount": 0},
        {"product_id": 2, "quantity": 3, "price": 30, "total_discount": 0},
        {"product_id": 3, "quantity": 4, "price": 25, "total_discount": 50}
      ],
      "total_price": 490,
      "total_discount": 50,
      "final_price": 440
    }
  }
  ```

---

## Limitations & Future Work

- Only three coupon types are implemented.
- No support for coupon validity periods, or user-specific constraints.
- Only percentage discounts are supported.

---

## Code Quality & Structure

- **Controllers:** [CouponController.java](src/main/java/com/prasanth/coupon_manage/controller/CouponController.java), [CouponApplicationController.java](src/main/java/com/prasanth/coupon_manage/controller/CouponApplicationController.java)
- **Services:** [CouponService.java](src/main/java/com/prasanth/coupon_manage/services/CouponService.java), [CouponApplicationService.java](src/main/java/com/prasanth/coupon_manage/services/CouponApplicationService.java)
- **Models:** [Coupon.java](src/main/java/com/prasanth/coupon_manage/models/Coupon.java), [Product.java](src/main/java/com/prasanth/coupon_manage/models/Product.java), [CouponType.java](src/main/java/com/prasanth/coupon_manage/models/CouponType.java)
- **DTOs:** [ApplicableCouponResponse.java](src/main/java/com/prasanth/coupon_manage/dto/ApplicableCouponResponse.java), [ApplyCouponResponse.java](src/main/java/com/prasanth/coupon_manage/dto/ApplyCouponResponse.java), [CouponResponse.java](src/main/java/com/prasanth/coupon_manage/dto/CouponResponse.java), [CouponRequest.java](src/main/java/com/prasanth/coupon_manage/dto/CouponRequest.java), [CartRequest.java](src/main/java/com/prasanth/coupon_manage/dto/CartRequest.java)

---

## How to Run

1. Configure your database in `src/main/resources/application.properties`.
2. Build and run the project using Maven:
   ```sh
   ./mvnw spring-boot:run
   ```
3. Use the API endpoints as described above.

---