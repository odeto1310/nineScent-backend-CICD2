package shop.ninescent.mall.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.order.domain.Orders;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    // You can add custom query methods here if needed
    List<Orders> findByUserNo(Long userNo);

}