package com.foodibd.backend.dto.cart.getCartItemDetails;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemOptionDTO {

    private Integer optionId;
    private String optionName;
    private Integer extraPrice;
    private Boolean selected;
}
