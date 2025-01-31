package shop.ninescent.mall.faq.dto;

import lombok.Data;

@Data
public class UpdateFaqRequestDTO {

    private String category;

    private String title;

    private String content;
}
