package shop.ninescent.mall.category.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemListResponseDTO {

    private Long itemId;

    private String itemName;
}
