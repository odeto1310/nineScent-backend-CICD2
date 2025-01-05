package shop.ninescent.mall.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.order.domain.Item;


public interface ItemRepository extends JpaRepository<Item, Long> {
}
