package com.foodibd.backend.controller;

import com.foodibd.backend.dto.cart.getActiveCart.CartResponseDTO;
import com.foodibd.backend.dto.cart.getCartItemDetails.CartItemDetailsResponseDTO;
import com.foodibd.backend.dto.cart.addItemToCart.AddToCartRequestDTO;
import com.foodibd.backend.dto.cart.addItemToCart.AddToCartResponseDTO;
import com.foodibd.backend.dto.cart.updateCartItem.UpdateCartItemRequestDTO;
import com.foodibd.backend.dto.cart.updateCartItem.UpdateCartItemResponseDTO;
import com.foodibd.backend.dto.cart.removeCartItem.RemoveCartItemResponseDTO;
import com.foodibd.backend.dto.cart.clearCart.ClearCartResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    //get active cart for the user
    @GetMapping
    public ResponseEntity<CartResponseDTO> getActiveCart(
            @RequestHeader("Authorization") String authorizationHeader) {

        // TODO: call service layer
        return ResponseEntity.ok().build();
    }
    
    //get cart item details by cart item id
    @GetMapping("/items/{cart_item_id}")
    public ResponseEntity<CartItemDetailsResponseDTO> getCartItemDetails(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("cart_item_id") Integer cartItemId) {

        // TODO: call service layer
        return ResponseEntity.ok().build();
    }

    //add item to cart
    @PostMapping("/items")
    public ResponseEntity<AddToCartResponseDTO> addItemToCart(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody AddToCartRequestDTO request) {

        // TODO: call service layer
        // 404 if restaurant or item not found
        // 409 if cart contains items from a different restaurant
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/items/{cart_item_id}")
    public ResponseEntity<UpdateCartItemResponseDTO> updateCartItem(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("cart_item_id") Integer cartItemId,
            @RequestBody UpdateCartItemRequestDTO request) {

        // TODO: call service layer
        // 404 if cart item not found
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{cart_item_id}")
    public ResponseEntity<RemoveCartItemResponseDTO> removeCartItem(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("cart_item_id") Integer cartItemId) {

        // TODO: call service layer
        // 404 if cart item not found
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<ClearCartResponseDTO> clearCart(
            @RequestHeader("Authorization") String authorizationHeader) {

        // TODO: call service layer
        return ResponseEntity.ok().build();
    }
}