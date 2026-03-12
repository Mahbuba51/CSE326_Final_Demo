package com.foodibd.backend.dto.cart.updateCartItem;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCartItemResponseDTO {

    private Integer cartItemId;
    private Integer quantity;
    private Integer lineTotal;
    private Integer cartTotal;
}
