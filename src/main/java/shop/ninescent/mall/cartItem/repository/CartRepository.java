package shop.ninescent.mall.cartItem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.ninescent.mall.cartItem.domain.Cart;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.userNo = :userNo")
    Optional<Cart> findByUserUserNo(@Param("userNo") Long userNo);
}
