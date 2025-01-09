package shop.ninescent.mall.mypage.repository;

import shop.ninescent.mall.mypage.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.mypage.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long userId);
}