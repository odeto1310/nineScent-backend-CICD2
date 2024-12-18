package shop.ninescent.mall.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.review.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByItemId(Long itemId);

    List<Review> findByUserNo(Long userNo);
}
