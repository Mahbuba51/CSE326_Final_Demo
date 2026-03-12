package com.foodibd.backend.dto.cart.getCartItemDetails;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDetailsResponseDTO {

    private Integer cartItemId;
    private Integer itemId;
    private String name;
    private Integer basePrice;
    private Integer quantity;
    private String specialInstructions;
    private String thumbnailUrl;
    private List<CartItemCustomizationDTO> customizations;
    private List<CartItemAddonDTO> addons;
    private Integer itemTotalPrice;
}
