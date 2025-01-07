package shop.ninescent.mall.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ninescent.mall.item.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}