package com.foodibd.backend.dto.cart.getActiveCart;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {

    private Integer cartItemId;
    private Integer itemId;
    private String name;
    private List<String> customizations;
    private Integer unitPrice;
    private Integer quantity;
    private Integer lineTotal;
}
