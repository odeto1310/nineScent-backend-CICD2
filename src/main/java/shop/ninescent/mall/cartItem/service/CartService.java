package shop.ninescent.mall.cartItem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.cartItem.domain.Cart;
import shop.ninescent.mall.cartItem.domain.CartItem;
import shop.ninescent.mall.cartItem.repository.CartItemRepository;
import shop.ninescent.mall.cartItem.repository.CartRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public int getCartCount(long userNo) {
        List<Cart> carts = cartRepository.findByUserNo(userNo);
        if (carts.isEmpty()) {
            return 0;
        }
        Cart cart = carts.get(0);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());
        return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }

}