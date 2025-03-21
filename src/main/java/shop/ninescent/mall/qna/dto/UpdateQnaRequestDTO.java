package shop.ninescent.mall.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQnaRequestDTO {

    private String qnaCategory;

    private String title;

    private String content;

    private String attachment;
}
