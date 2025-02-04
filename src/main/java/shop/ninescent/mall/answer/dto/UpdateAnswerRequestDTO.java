package shop.ninescent.mall.answer.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateAnswerRequestDTO {

    private String answer;

    private LocalDateTime createdDate;
}
