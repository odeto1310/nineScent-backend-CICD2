package shop.ninescent.cartItem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ninescent.cartItem.domain.Cart;
import shop.ninescent.cartItem.domain.CartItem;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByCartId(Long cartId);
}

