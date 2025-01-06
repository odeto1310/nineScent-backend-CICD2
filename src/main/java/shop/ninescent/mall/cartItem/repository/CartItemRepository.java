package shop.ninescent.mall.cartItem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ninescent.mall.cartItem.domain.CartItem;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>  {
    List<CartItem> findByCartId(Long cartId);
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);  // cartId와 itemId로 CartItem 조회
    List<CartItem> findByCartIdAndIsSelectedTrue(Long cartId);  // 'isSelected'가 true인 아이템 조회
}
