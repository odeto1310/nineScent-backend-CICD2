package shop.ninescent.mall.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaResponseDTO {

    private Long questionId;

    private Long itemId;

    private Long userNo;

    private String title;

    private String name;

    private String qnaCategory;

    private String content;

    private boolean isDone;

    private String attachment;

    private LocalDateTime createdDate;
}
