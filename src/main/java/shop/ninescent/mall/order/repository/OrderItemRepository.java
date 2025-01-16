package shop.ninescent.mall.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.order.domain.OrderItems;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

    // Find all order items by orderId
    List<OrderItems> findByOrderId(Long orderId);

}
