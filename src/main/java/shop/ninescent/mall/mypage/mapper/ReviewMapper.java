package shop.ninescent.mall.mypage.mapper;

import shop.ninescent.mall.mypage.dto.ReviewDto;
import shop.ninescent.mall.mypage.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    // DTO → Entity 변환
    public Review toEntity(ReviewDto dto) {
        Review entity = new Review();
        entity.setReviewId(dto.getReviewId());
        entity.setItemId(dto.getItemId());
        entity.setUserNo(dto.getUserNo());
        entity.setRating(dto.getRating());
        entity.setContent(dto.getContent());
        entity.setReviewImage(dto.getReviewImage());
        return entity;
    }

    // Entity → DTO 변환
    public ReviewDto toDto(Review entity) {
        ReviewDto dto = new ReviewDto();
        dto.setReviewId(entity.getReviewId());
        dto.setItemId(entity.getItemId());
        dto.setUserNo(entity.getUserNo());
        dto.setRating(entity.getRating());
        dto.setContent(entity.getContent());
        dto.setReviewImage(entity.getReviewImage());
        return dto;
    }
}
