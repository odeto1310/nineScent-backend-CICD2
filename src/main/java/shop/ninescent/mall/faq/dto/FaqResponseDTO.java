package shop.ninescent.mall.faq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FaqResponseDTO {

    private Long faqId;

    private String category;

    private String title;

    private String content;

    private LocalDateTime regDate;
}
