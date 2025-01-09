package shop.ninescent.mall.mypage.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ReviewDto {
    private Long reviewId;
    private Long itemId;
    private Long userNo;
    private int rating;
    private String content;
    private String reviewImage;

    // Getters and Setters
}
