package shop.ninescent.mall.cartItem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    @JsonProperty("cartId")
    private Long cartId;
    @JsonProperty("itemId")
    private Long itemId;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("action")
    private String action;     // increase, decrease, set
}