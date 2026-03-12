package com.foodibd.backend.dto.cart.addItemToCart;
 
import lombok.*;
import java.util.List;
import java.util.Map;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartRequestDTO {
 
    private Integer restaurantId;
    private Integer itemId;
    private Integer quantity;
    private Map<Integer, Integer> customizations;    //map from customization id to selected option id
    private List<Integer> addonIds;
    private String specialInstruction;
}
 