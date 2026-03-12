package com.foodibd.backend.dto.cart.getCartItemDetails;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemAddonDTO {

    private Integer addonId;
    private String name;
    private Integer price;
    private Boolean selected;
}
