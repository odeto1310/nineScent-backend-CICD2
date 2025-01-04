package shop.ninescent.mall.cartItem.domain;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long cartId;
    private Long itemId;
    private Integer quantity;
    private String action;     // increase, decrease, set
}