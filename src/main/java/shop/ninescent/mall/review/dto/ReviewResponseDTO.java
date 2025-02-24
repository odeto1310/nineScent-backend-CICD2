package shop.ninescent.mall.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDTO {

    private Long reviewId;

    private Long itemId;

    private Long userNo;

    private String name;

    private int rating;

    private String content;

    private int likeCnt; //좋아요

    private LocalDateTime createdDate;

    private String reviewImage;
}
