package shop.ninescent.mall.category.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubCategoryResponseDTO {

    private Long subCategoryId;

    private String subCategoryName;
}
