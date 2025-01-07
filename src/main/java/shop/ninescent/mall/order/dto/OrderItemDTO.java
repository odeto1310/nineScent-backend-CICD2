package shop.ninescent.mall.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import shop.ninescent.mall.address.domain.Address;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Long itemId;
    private String itemName;
    private Integer quantity;
    private Long originalPrice;
    private Long discountedPrice;
    private Address address; // Address details

}
