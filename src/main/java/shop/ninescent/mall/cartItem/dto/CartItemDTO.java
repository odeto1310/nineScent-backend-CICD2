package shop.ninescent.mall.cartItem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    private String itemSize;
    private BigDecimal price;
    private Integer discountRate;
    private BigDecimal discountPrice;
    private Integer stock;
    private String mainPhoto;
}