package shop.ninescent.mall.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewRequestDTO {

    private int rating;

    private String content;

    private String reviewImage;
}
