package shop.ninescent.mall.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.category.domain.Category;


public interface GetCategoryRepository extends JpaRepository<Category, Long> {
}
