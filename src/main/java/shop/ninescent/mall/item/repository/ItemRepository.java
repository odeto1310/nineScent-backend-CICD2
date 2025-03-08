package shop.ninescent.mall.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.ninescent.mall.item.domain.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i ORDER BY FUNCTION('RAND') LIMIT 4")
    List<Item> recommendItems();
}