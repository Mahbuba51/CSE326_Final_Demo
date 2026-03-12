package com.foodibd.backend.dto.cart.getCartItemDetails;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemCustomizationDTO {

    private Integer customizationId;
    private String description;
    private List<CartItemOptionDTO> options;
}
