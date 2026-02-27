#!/bin/bash

# ============================================================
# Person 1 - Backend Lead: Entity & Repository Setup Script
# Run this from the ROOT of your project (same level as backend/)
# Usage: bash setup_backend_structure.sh
# ============================================================

BASE="backend/src/main/java/com/foodibd/backend"

echo "Creating directory structure..."
mkdir -p "$BASE/entity/embedded"
mkdir -p "$BASE/entity/enums"
mkdir -p "$BASE/repository"
mkdir -p "$BASE/config"
echo "Directories created."

# ============================================================
# ENUMS
# ============================================================

cat > "$BASE/entity/enums/OrderStatus.java" << 'EOF'
package com.foodibd.backend.entity.enums;

public enum OrderStatus {
    CREATED,
    PAID,
    ACCEPTED,
    PREPARING,
    READY,
    PICKED_UP,
    DELIVERED,
    CANCELLED
}
EOF

cat > "$BASE/entity/enums/PaymentOption.java" << 'EOF'
package com.foodibd.backend.entity.enums;

public enum PaymentOption {
    COD,
    ONLINE
}
EOF

cat > "$BASE/entity/enums/PaymentStatus.java" << 'EOF'
package com.foodibd.backend.entity.enums;

public enum PaymentStatus {
    PENDING,
    PAID,
    FAILED
}
EOF

cat > "$BASE/entity/enums/NotificationType.java" << 'EOF'
package com.foodibd.backend.entity.enums;

public enum NotificationType {
    ORDER_CONFIRMED,
    ORDER_REJECTED,
    ORDER_PREPARING,
    ORDER_READY,
    ORDER_DELIVERED,
    PAYMENT_RECEIVED
}
EOF

echo "Enums created."

# ============================================================
# EMBEDDED
# ============================================================

cat > "$BASE/entity/embedded/Address.java" << 'EOF'
package com.foodibd.backend.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
EOF

echo "Address embedded created."

# ============================================================
# ENTITIES
# ============================================================

cat > "$BASE/entity/Customer.java" << 'EOF'
package com.foodibd.backend.entity;

import com.foodibd.backend.entity.embedded.Address;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerID;

    private String name;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street",     column = @Column(name = "address_street")),
        @AttributeOverride(name = "city",       column = @Column(name = "address_city")),
        @AttributeOverride(name = "state",      column = @Column(name = "address_state")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "address_postal_code")),
        @AttributeOverride(name = "country",    column = @Column(name = "address_country"))
    })
    private Address address;
}
EOF

cat > "$BASE/entity/Restaurant.java" << 'EOF'
package com.foodibd.backend.entity;

import com.foodibd.backend.entity.embedded.Address;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurantID;

    private String name;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street",     column = @Column(name = "address_street")),
        @AttributeOverride(name = "city",       column = @Column(name = "address_city")),
        @AttributeOverride(name = "state",      column = @Column(name = "address_state")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "address_postal_code")),
        @AttributeOverride(name = "country",    column = @Column(name = "address_country"))
    })
    private Address address;

    @ElementCollection
    @CollectionTable(name = "restaurant_cuisine_types", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "cuisine_type")
    private List<String> cuisineTypes;

    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Menu menu;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    private Double rating;
    private Boolean available;
}
EOF

cat > "$BASE/entity/Menu.java" << 'EOF'
package com.foodibd.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "menus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer menuID;

    @OneToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuItem> items;
}
EOF

cat > "$BASE/entity/MenuItem.java" << 'EOF'
package com.foodibd.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "menu_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    private String name;
    private String description;
    private Double price;
    private Boolean availability;

    @ElementCollection
    @CollectionTable(name = "menu_item_categories", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "category")
    private List<String> categories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
EOF

cat > "$BASE/entity/Cart.java" << 'EOF'
package com.foodibd.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartID;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();
}
EOF

cat > "$BASE/entity/CartItem.java" << 'EOF'
package com.foodibd.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemID;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToMany
    @JoinTable(
        name = "cart_item_addons",
        joinColumns = @JoinColumn(name = "cart_item_id"),
        inverseJoinColumns = @JoinColumn(name = "addon_item_id")
    )
    private List<MenuItem> addOns;

    public Double calculateSubTotal() {
        double addOnTotal = addOns == null ? 0.0 :
            addOns.stream().mapToDouble(MenuItem::getPrice).sum();
        return (menuItem.getPrice() + addOnTotal) * quantity;
    }
}
EOF

cat > "$BASE/entity/Order.java" << 'EOF'
package com.foodibd.backend.entity;

import com.foodibd.backend.entity.embedded.Address;
import com.foodibd.backend.entity.enums.OrderStatus;
import com.foodibd.backend.entity.enums.PaymentOption;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID;

    private Integer cartID;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Boolean paymentStatus;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street",     column = @Column(name = "delivery_street")),
        @AttributeOverride(name = "city",       column = @Column(name = "delivery_city")),
        @AttributeOverride(name = "state",      column = @Column(name = "delivery_state")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "delivery_postal_code")),
        @AttributeOverride(name = "country",    column = @Column(name = "delivery_country"))
    })
    private Address deliveryAddress;

    private LocalDateTime createdAt;
    private LocalDateTime estimatedDeliveryTime;

    @Enumerated(EnumType.STRING)
    private PaymentOption paymentOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
EOF

cat > "$BASE/entity/OrderItem.java" << 'EOF'
package com.foodibd.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemID;

    private Integer quantity;
    private Double priceAtPurchase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public Double calculatePrice() {
        return priceAtPurchase * quantity;
    }
}
EOF

cat > "$BASE/entity/Payment.java" << 'EOF'
package com.foodibd.backend.entity;

import com.foodibd.backend.entity.enums.PaymentOption;
import com.foodibd.backend.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentID;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentOption method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Double amount;
    private String transactionRef;
}
EOF

cat > "$BASE/entity/Rider.java" << 'EOF'
package com.foodibd.backend.entity;

import com.foodibd.backend.entity.embedded.Address;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "riders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer riderID;

    private String name;
    private String phoneNumber;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street",     column = @Column(name = "location_street")),
        @AttributeOverride(name = "city",       column = @Column(name = "location_city")),
        @AttributeOverride(name = "state",      column = @Column(name = "location_state")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "location_postal_code")),
        @AttributeOverride(name = "country",    column = @Column(name = "location_country"))
    })
    private Address currentLocation;
}
EOF

echo "Entities created."

# ============================================================
# REPOSITORIES
# ============================================================

cat > "$BASE/repository/CustomerRepository.java" << 'EOF'
package com.foodibd.backend.repository;

import com.foodibd.backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
}
EOF

cat > "$BASE/repository/RestaurantRepository.java" << 'EOF'
package com.foodibd.backend.repository;

import com.foodibd.backend.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findByNameContainingIgnoreCase(String name);
    List<Restaurant> findByAvailableTrue();
}
EOF

cat > "$BASE/repository/MenuRepository.java" << 'EOF'
package com.foodibd.backend.repository;

import com.foodibd.backend.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    Optional<Menu> findByRestaurantRestaurantID(Integer restaurantId);
}
EOF

cat > "$BASE/repository/MenuItemRepository.java" << 'EOF'
package com.foodibd.backend.repository;

import com.foodibd.backend.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    List<MenuItem> findByMenuMenuID(Integer menuId);
    List<MenuItem> findByAvailabilityTrue();
}
EOF

cat > "$BASE/repository/CartRepository.java" << 'EOF'
package com.foodibd.backend.repository;

import com.foodibd.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByCustomerCustomerID(Integer customerId);
}
EOF

cat > "$BASE/repository/CartItemRepository.java" << 'EOF'
package com.foodibd.backend.repository;

import com.foodibd.backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCartCartID(Integer cartId);
    void deleteByCartCartID(Integer cartId);
}
EOF

cat > "$BASE/repository/OrderRepository.java" << 'EOF'
package com.foodibd.backend.repository;

import com.foodibd.backend.entity.Order;
import com.foodibd.backend.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomerCustomerID(Integer customerId);
    List<Order> findByRestaurantRestaurantID(Integer restaurantId);
    List<Order> findByStatus(OrderStatus status);
}
EOF

cat > "$BASE/repository/OrderItemRepository.java" << 'EOF'
package com.foodibd.backend.repository;

import com.foodibd.backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrderOrderID(Integer orderId);
}
EOF

cat > "$BASE/repository/PaymentRepository.java" << 'EOF'
package com.foodibd.backend.repository;

import com.foodibd.backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findByOrderOrderID(Integer orderId);
}
EOF

cat > "$BASE/repository/RiderRepository.java" << 'EOF'
package com.foodibd.backend.repository;

import com.foodibd.backend.entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Integer> {
}
EOF

echo "Repositories created."

# ============================================================
# CORS CONFIG
# ============================================================

cat > "$BASE/config/CorsConfig.java" << 'EOF'
package com.foodibd.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4173", "http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                        .allowedHeaders("*");
            }
        };
    }
}
EOF

echo "CorsConfig created."

# ============================================================
# DONE
# ============================================================

echo ""
echo "All files created successfully!"
echo ""
echo "Created structure:"
find "$BASE/entity" "$BASE/repository" "$BASE/config" -name "*.java" | sort
echo ""
echo "Next step: docker compose up --build"
