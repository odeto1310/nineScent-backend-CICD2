package shop.ninescent.cartItem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.ninescent.cartItem.domain.Cart;
import shop.ninescent.cartItem.domain.CartItem;
import shop.ninescent.cartItem.repository.CartItemRepository;
import shop.ninescent.cartItem.repository.CartRepository;

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