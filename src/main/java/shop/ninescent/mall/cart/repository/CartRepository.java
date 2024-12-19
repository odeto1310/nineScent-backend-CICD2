package shop.ninescent.mall.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ninescent.mall.cart.domain.CartItem;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long>  {
    List<CartItem> findByCartId(Long cartId);
}
