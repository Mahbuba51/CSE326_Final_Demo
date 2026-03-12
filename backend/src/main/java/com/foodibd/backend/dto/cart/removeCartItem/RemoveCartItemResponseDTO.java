package com.foodibd.backend.dto.cart.removeCartItem;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemoveCartItemResponseDTO {

    private String message;
    private Integer cartTotal;
}
