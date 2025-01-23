package shop.ninescent.mall.cartItem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private Long itemId;
    private String itemName;
    private Integer quantity;
    private Boolean isSelected;
    @JsonProperty("action")
    private String action;     // increase, decrease, set
}