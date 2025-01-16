package shop.ninescent.mall.mypage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private Long itemId;
    private Long userNo;
    private int rating;
    private String content;
    private String reviewImage;
    private Date createdDate;


}
