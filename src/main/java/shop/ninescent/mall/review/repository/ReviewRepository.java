package shop.ninescent.mall.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.ninescent.mall.review.domain.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByItemId(Long itemId);

    List<Review> findByUserNo(Long userNo);

    Page<Review> findByItemId(Long itemId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.itemId = :itemId")
    Double getAverageRating(@Param("itemId") Long itemId);

    @Query("SELECT r.rating, COUNT(r) FROM Review r WHERE r.itemId = :itemId GROUP BY r.rating")
    List<Object[]> getRatingCounts(@Param("itemId") Long itemId);
}
