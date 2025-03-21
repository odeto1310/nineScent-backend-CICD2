package shop.ninescent.mall.category.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDTO {

    private Long categoryId;

    private String categoryName;
}
