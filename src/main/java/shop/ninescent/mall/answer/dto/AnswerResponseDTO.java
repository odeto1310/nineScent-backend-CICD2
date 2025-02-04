package shop.ninescent.mall.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerResponseDTO {

    private Long answerId;

    private Long questionId;

    private String answer;

    private LocalDateTime createdDate;
}
