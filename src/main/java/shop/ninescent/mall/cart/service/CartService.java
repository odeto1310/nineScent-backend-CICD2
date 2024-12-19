package shop.ninescent.mall.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.cart.domain.CartItem;
import shop.ninescent.mall.cart.repository.CartRepository;

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

