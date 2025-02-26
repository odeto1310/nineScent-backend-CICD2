package shop.ninescent.mall.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.category.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
