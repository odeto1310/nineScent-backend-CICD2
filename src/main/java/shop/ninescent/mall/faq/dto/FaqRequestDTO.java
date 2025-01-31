package shop.ninescent.mall.faq.dto;

import lombok.Data;

@Data
public class FaqRequestDTO {

    private String category;

    private String title;

    private String content;
}
