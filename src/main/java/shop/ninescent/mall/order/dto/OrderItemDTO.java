package shop.ninescent.mall.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private int orderItemId;
    private String itemName;
    private Long itemId;  // 상품 ID
    private String mainPhoto;
    private int quantity;  // 수량
    private long originalPrice;  // 원래 가격
    private long discountedPrice;  // 할인된 가격
}