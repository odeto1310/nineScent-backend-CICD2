package shop.ninescent.mall.orderhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.ninescent.mall.order.dto.OrderItemDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryDTO {
    private Long orderId;
    private String orderDate;
    private Boolean deliveryDone;
    private Boolean paymentDone;
    private Boolean refundChangeDone;
    private List<OrderItemDTO> items;
}
