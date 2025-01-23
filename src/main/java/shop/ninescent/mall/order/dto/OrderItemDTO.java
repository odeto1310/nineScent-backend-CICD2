package shop.ninescent.mall.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import shop.ninescent.mall.address.domain.Address;
import shop.ninescent.mall.item.domain.Item;
import shop.ninescent.mall.member.domain.User;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Long itemId;
    private String itemName;
    private Integer quantity;
    private Long originalPrice;
    private Long discountedPrice;
    private Long userId;
    private String userName;
    private Long addressId;
    private String addressDetail;
    private Long shippingFee;
}
