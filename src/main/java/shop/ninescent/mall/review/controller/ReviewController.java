package shop.ninescent.mall.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.review.dto.ReviewRequestDTO;
import shop.ninescent.mall.review.dto.ReviewResponseDTO;
import shop.ninescent.mall.review.dto.UpdateReviewRequestDTO;
import shop.ninescent.mall.review.service.ReviewService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        ReviewResponseDTO responseDTO = reviewService.createReview(reviewRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<ReviewResponseDTO>> findReviewByItemId(@PathVariable Long itemId) {
        List<ReviewResponseDTO> responseDTOList = reviewService.findReviewByItemId(itemId);
        return ResponseEntity.ok(responseDTOList);
    }

    @GetMapping("/list/{itemId}")
    public Page<ReviewResponseDTO> findReviewByItemId(@PathVariable Long itemId, @PageableDefault(size = 5) Pageable pageable) {
        return reviewService.findReviewByPage(itemId, pageable);
    }

    @GetMapping("/user/{userNo}")
    public ResponseEntity<List<ReviewResponseDTO>> findReviewByUserNo(@PathVariable Long userNo) {
        List<ReviewResponseDTO> responseDTOList = reviewService.findReviewByUserNo(userNo);
        return ResponseEntity.ok(responseDTOList);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> findReviewById(@PathVariable Long reviewId) {
        reviewService.findReviewById(reviewId);
        return ResponseEntity.ok(reviewService.findReviewById(reviewId));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long reviewId, @RequestBody UpdateReviewRequestDTO updateDTO) {
        ReviewResponseDTO responseDTO = reviewService.updateReview(reviewId, updateDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rating/{itemId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long itemId) {
        Double averageRating = reviewService.getAverageRating(itemId);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/rating-count/{itemId}")
    public ResponseEntity<Map<Integer, Long>> getRatingCounts(@PathVariable Long itemId) {
        Map<Integer, Long> ratingCounts = reviewService.getRatingCounts(itemId);
        return ResponseEntity.ok(ratingCounts);
    }
}

