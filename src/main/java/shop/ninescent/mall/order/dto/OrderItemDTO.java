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
    private int quantity;
    private long originalPrice;
    private long discountedPrice;
}