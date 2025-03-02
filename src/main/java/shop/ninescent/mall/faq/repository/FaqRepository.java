package shop.ninescent.mall.faq.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.ninescent.mall.faq.domain.Faq;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {

    // 특정 카테고리의 FAQ 가져오기
    List<Faq> findByCategory(String category);

    Page<Faq> findByCategory(String category, Pageable pageable);

    // 카테고리 목록 중복 제거 후 가져오기
    @Query("SELECT DISTINCT f.category FROM Faq f")
    List<String> findDistinctCategories();
}
