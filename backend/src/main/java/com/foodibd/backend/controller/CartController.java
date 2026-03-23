package com.foodibd.backend.controller;

import com.foodibd.backend.dto.cart.promo.PromoRequestDTO;
import com.foodibd.backend.dto.cart.promo.PromoResponseDTO;
import com.foodibd.backend.dto.cart.verify.VerifyRequestDTO;
import com.foodibd.backend.dto.cart.verify.VerifyResponseDTO;
import com.foodibd.backend.dto.cart.bill.BillRequestDTO;
import com.foodibd.backend.dto.cart.bill.BillResponseDTO;
import com.foodibd.backend.dto.cart.checkout.CheckoutRequestDTO;
import com.foodibd.backend.entity.Order;
import com.foodibd.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/promo")
    public ResponseEntity<PromoResponseDTO> validatePromo(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody PromoRequestDTO request) {
        return ResponseEntity.ok(cartService.validatePromo(request));
    }

    @PostMapping("/bill")
    public ResponseEntity<BillResponseDTO> generateBill(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody BillRequestDTO request) {
        return ResponseEntity.ok(cartService.generateBill(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyResponseDTO> verify(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody VerifyRequestDTO request) {
        return ResponseEntity.ok(cartService.verify(request));
    }

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody CheckoutRequestDTO request) {
        return ResponseEntity.ok(cartService.checkout(request));
    }
}