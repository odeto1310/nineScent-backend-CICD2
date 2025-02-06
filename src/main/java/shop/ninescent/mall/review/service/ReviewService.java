package shop.ninescent.mall.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.review.dto.ReviewRequestDTO;
import shop.ninescent.mall.review.dto.ReviewResponseDTO;
import shop.ninescent.mall.review.domain.Review;
import shop.ninescent.mall.review.dto.UpdateReviewRequestDTO;
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

    public List<ReviewResponseDTO> findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDTO> findReviewByUserNo(Long userNo) {
        return reviewRepository.findByUserNo(userNo).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ReviewResponseDTO updateReview(Long reviewId, UpdateReviewRequestDTO updateDTO) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

        review.setRating(updateDTO.getRating());
        review.setContent(updateDTO.getContent());
        review.setReviewImage(updateDTO.getReviewImage());

        Review updatedReview = reviewRepository.save(review);
        return toResponseDTO(updatedReview);
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다"));

        reviewRepository.delete(review);
    }

    private ReviewResponseDTO toResponseDTO(Review review) {
        return ReviewResponseDTO.builder()
                .reviewId(review.getReviewId())
                .itemId(review.getItemId())
                .userNo(review.getUserNo())
                .rating(review.getRating())
                .content(review.getContent())
                .reviewImage(review.getReviewImage())
                .createdDate(review.getCreatedDate())
                .build();
    }
}
