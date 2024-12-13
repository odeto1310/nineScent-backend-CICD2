package shop.ninescent.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.ninescent.cart.domain.CartItem;
import shop.ninescent.cart.repository.CartRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<CartItem> getAllItems(Long cartId) {
        return cartRepository.findByCartId(cartId);
    }
}

