package shop.ninescent.mall.qna.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="qna_board")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private Long userNo;

    @Column(nullable = false)
    private String qnaCategory;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String attachment;

    @Column(nullable = false)
    private boolean isDone;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;
}
