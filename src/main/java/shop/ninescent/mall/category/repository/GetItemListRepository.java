package shop.ninescent.mall.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.order.domain.Item;

import java.util.List;

public interface GetItemListRepository extends JpaRepository<Item, Long> {

    List<Item> findBySubCategoryId(Long subCategoryId);

    List<Item> findByItemId(Long itemId);
}
