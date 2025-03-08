package shop.ninescent.mall.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import shop.ninescent.mall.item.domain.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // 특정 카테고리에 속하는 아이템 리스트 조회 (categoryId 기준)
    @Query("SELECT i FROM Item i WHERE i.categoryId = :categoryId")
    List<Item> findByCategoryId(@Param("categoryId") Long categoryId);

    // 특정 하위 카테고리에 속하는 아이템 리스트 조회 (subCategoryId 기준)
    @Query("SELECT i FROM Item i WHERE i.subCategoryId = :subCategoryId")
    List<Item> findBySubCategoryId(@Param("subCategoryId") Long subCategoryId);

    // 특정 카테고리와 하위 카테고리에 속하는 아이템 리스트 조회
    @Query("SELECT i FROM Item i WHERE i.categoryId = :categoryId AND i.subCategoryId = :subCategoryId")
    List<Item> findByCategoryIdAndSubCategoryId(@Param("categoryId") Long categoryId, @Param("subCategoryId") Long subCategoryId);

    @Query("SELECT i FROM Item i ORDER BY FUNCTION('RAND') LIMIT 4")
    List<Item> recommendItems();
}