package shop.ninescent.mall.cartItem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.ninescent.mall.cartItem.domain.Cart;
import shop.ninescent.mall.cartItem.domain.CartItem;
import shop.ninescent.mall.item.domain.Item;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart); // Finds items by Cart object

    CartItem findByCartAndItem(Cart cart, Item item); // Finds a CartItem by Cart and Item

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.cartId = :cartId AND ci.isSelected = true")
    List<CartItem> findByCartIdAndIsSelectedTrue(@Param("cartId") Long cartId);

    List<CartItem> findByCartAndIsSelectedTrue(Cart cart); // Finds selected items by Cart object
}