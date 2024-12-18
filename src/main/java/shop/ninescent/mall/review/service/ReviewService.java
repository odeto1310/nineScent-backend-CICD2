package shop.ninescent.mall.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.review.dto.ReviewRequestDTO;
import shop.ninescent.mall.review.dto.ReviewResponseDTO;
import shop.ninescent.mall.review.entity.Review;
import shop.ninescent.mall.review.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        Review review = Review.builder()
                .itemId(reviewRequestDTO.getItemId())
                .userNo(reviewRequestDTO.getUserNo())
                .rating(reviewRequestDTO.getRating())
                .content(reviewRequestDTO.getContent())
                .reviewImage(reviewRequestDTO.getReviewImage())
                .createdDate(LocalDateTime.now())
                .build();

        Review savedReview = reviewRepository.save(review);
        return toResponseDTO(savedReview);
    }

    public List<ReviewResponseDTO> findReviewByItemId(Long itemId) {
        return reviewRepository.findByItemId(itemId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private ReviewResponseDTO toResponseDTO(Review review) {
        return ReviewResponseDTO.builder()
                .reviewId(review.getReviewId())
                .itemId(review.getItemId())
                .userNo(review.getUserNo())
                .rating(review.getRating())
                .content(review.getContent())
                .reviewImage(review.getReviewImage())
                .createdDate(LocalDateTime.now())
                .build();
    }
}
