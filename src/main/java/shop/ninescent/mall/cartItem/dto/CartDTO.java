package shop.ninescent.mall.cartItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartDTO {
    private Long cartId;
    private List<CartItemDTO> cartItems;
}