package shop.ninescent.mall.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequestDTO {
    private Long userNo;
    private Long addressNo;
    private List<OrderItemDTO> orderItems;
    private boolean paymentDone;
}
