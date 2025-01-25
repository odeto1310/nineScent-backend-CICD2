package shop.ninescent.mall.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.review.dto.ReviewRequestDTO;
import shop.ninescent.mall.review.dto.ReviewResponseDTO;
import shop.ninescent.mall.review.dto.UpdateReviewRequestDTO;
import shop.ninescent.mall.review.service.ReviewService;

import java.util.List;

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

    @GetMapping("/user/{userNo}")
    public ResponseEntity<List<ReviewResponseDTO>> findReviewByUserNo(@PathVariable Long userNo) {
        List<ReviewResponseDTO> responseDTOList = reviewService.findReviewByUserNo(userNo);
        return ResponseEntity.ok(responseDTOList);
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
}

