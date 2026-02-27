package com.foodibd.backend.repository;

import com.foodibd.backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCartCartID(Integer cartId);
    void deleteByCartCartID(Integer cartId);
}
