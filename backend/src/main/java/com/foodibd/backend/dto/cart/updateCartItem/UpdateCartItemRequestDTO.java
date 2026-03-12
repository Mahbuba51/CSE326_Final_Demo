package com.foodibd.backend.dto.cart.updateCartItem;

import lombok.*;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCartItemRequestDTO {

    private Integer quantity;
    private String specialInstructions;
    private Map<Integer, Integer> customizations;    //map from customization id to selected option id
    private List<Integer> addonIds;
}
