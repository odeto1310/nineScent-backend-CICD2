package shop.ninescent.mall.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.member.domain.User;
import shop.ninescent.mall.member.repository.UserRepository;
import shop.ninescent.mall.review.dto.ReviewRequestDTO;
import shop.ninescent.mall.review.dto.ReviewResponseDTO;
import shop.ninescent.mall.review.domain.Review;
import shop.ninescent.mall.review.dto.UpdateReviewRequestDTO;
import shop.ninescent.mall.review.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

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

    public ReviewResponseDTO findReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 review가 존재하지 않습니다."));

        return toResponseDTO(review);
    }

    public List<ReviewResponseDTO> findReviewByUserNo(Long userNo) {
        return reviewRepository.findByUserNo(userNo).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<ReviewResponseDTO> findReviewByPage(Long itemId, Pageable pageable) {
        return reviewRepository.findByItemId(itemId, pageable).map(this::toResponseDTO);
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

    public Double getAverageRating(Long itemId) {
        Double avgRating = reviewRepository.getAverageRating(itemId);
        return avgRating == null ? 0 : Math.round(avgRating * 10) / 10.0;
    }

    public Map<Integer, Long> getRatingCounts(Long itemId) {
        List<Object[]> result = reviewRepository.getRatingCounts(itemId);
        Map<Integer, Long> ratingCounts = new HashMap<>();

        for (int i = 1; i <= 5; i++) {
            ratingCounts.put(i, 0L);
        }

        for (Object[] row : result) {
            Integer rating = (Integer) row[0];
            Long count = (Long) row[1];
            ratingCounts.put(rating, count);
        }

        return ratingCounts;
    }

    private ReviewResponseDTO toResponseDTO(Review review) {
        String name = userRepository.findByUserNo(review.getUserNo())
                .map(User::getName)
                .orElse("Null");

        return ReviewResponseDTO.builder()
                .reviewId(review.getReviewId())
                .itemId(review.getItemId())
                .userNo(review.getUserNo())
                .name(name)
                .rating(review.getRating())
                .content(review.getContent())
                .reviewImage(review.getReviewImage())
                .createdDate(review.getCreatedDate())
                .build();
    }
}
