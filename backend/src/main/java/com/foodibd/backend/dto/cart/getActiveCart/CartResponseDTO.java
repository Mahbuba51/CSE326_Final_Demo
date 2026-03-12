package com.foodibd.backend.dto.cart.getActiveCart;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDTO {

    private Integer cartId;
    private Integer restaurantId;
    private String restaurantName;

    // Delivery address fields
    private String deliveryAddressLabel;
    private String deliveryAddressFullAddress;
    private Integer deliveryAddressId;

    private Integer estimatedDeliveryMin;
    private Integer estimatedDeliveryMax;

    private List<CartItemDTO> items;

    // Bill summary fields
    private Integer subtotal;
    private Integer deliveryFee;
    private Integer discount;
    private Integer total;

    private String promoCodeApplied;
}
