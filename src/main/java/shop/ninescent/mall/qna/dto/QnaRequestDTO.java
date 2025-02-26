package shop.ninescent.mall.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaRequestDTO {

    private Long itemId;

    private Long userNo;

    private String qnaCategory;

    private String title;

    private String content;

    private String attachment;
}
