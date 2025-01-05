package shop.ninescent.mall.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.order.domain.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    // You can add custom query methods here if needed
}