package shop.ninescent.mall.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ninescent.mall.order.domain.OrderItems;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

    // Find all order items by orderId
    List<OrderItems> findByOrderOrderId(Long orderId);

}
