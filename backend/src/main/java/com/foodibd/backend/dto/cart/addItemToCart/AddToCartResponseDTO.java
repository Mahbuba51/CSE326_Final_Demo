package com.foodibd.backend.dto.cart.addItemToCart;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartResponseDTO {

    private Integer cartItemId;
    private Integer cartId;
    private Integer itemId;
    private String name;
    private List<String> customizations;
    private Integer unitPrice;
    private Integer quantity;
    private Integer total;
}
