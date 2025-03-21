package shop.ninescent.mall.item.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendItem {

    private Long itemId;

    private String itemName;

    private String itemSize;

    private String itemTitle;

    private BigDecimal price;

    private Integer discountRate;

    private BigDecimal discountedPrice;

    private String mainPhoto;
}
