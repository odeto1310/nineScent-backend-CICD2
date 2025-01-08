package shop.ninescent.mall.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.item.domain.Item;

import java.util.List;

public interface GetItemListRepository extends JpaRepository<shop.ninescent.mall.item.domain.Item, Long> {

    List<shop.ninescent.mall.item.domain.Item> findBySubCategoryId(Long subCategoryId);

    List<Item> findByItemId(Long itemId);
}
