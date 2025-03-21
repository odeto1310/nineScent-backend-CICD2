package shop.ninescent.mall.cartItem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private Long itemId;
    private String itemName;
    private Integer quantity;
    private Integer stock;
    private Boolean isSelected;
    @JsonProperty("action")
    private String action;
    private BigDecimal price; //  상품 가격 추가
    private String description; //  상품 설명 추가
    private String mainPhoto; //  상품 대표 이미지 추가
    private String itemSize; //아이템 사이즈
}