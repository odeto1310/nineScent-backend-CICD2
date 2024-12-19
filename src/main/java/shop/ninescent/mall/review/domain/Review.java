package shop.ninescent.mall.review.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="review")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private Long userNo;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private String content;

    private String reviewImage;

    @Column(nullable = false)
    private LocalDateTime createdDate;
}
