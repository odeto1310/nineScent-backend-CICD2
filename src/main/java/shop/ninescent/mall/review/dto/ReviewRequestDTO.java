package shop.ninescent.mall.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDTO {

    private Long itemId;

    private Long userNo;

    private int rating;

    private String content;

    private String reviewImage;
}
