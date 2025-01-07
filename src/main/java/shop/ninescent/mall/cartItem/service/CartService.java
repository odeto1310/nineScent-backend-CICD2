package shop.ninescent.mall.cartItem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.cartItem.domain.Cart;
import shop.ninescent.mall.cartItem.domain.CartItem;
import shop.ninescent.mall.cartItem.repository.CartItemRepository;
import shop.ninescent.mall.cartItem.repository.CartRepository;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public int getCartCount(long cartId) {
        List<Cart> carts = cartRepository.findByCartId(cartId);
        if (carts.isEmpty()) {
            return 0;
        }
        Cart cart = carts.get(0);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());
        return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }

}