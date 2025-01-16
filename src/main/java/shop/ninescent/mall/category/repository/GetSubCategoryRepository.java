package shop.ninescent.mall.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.category.domain.SubCategory;

import java.util.List;

public interface GetSubCategoryRepository extends JpaRepository<SubCategory, Long> {

    List<SubCategory> findByCategoryId(Long categoryId);
}
