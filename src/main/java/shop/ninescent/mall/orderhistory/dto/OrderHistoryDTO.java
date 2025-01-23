package shop.ninescent.mall.orderhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.ninescent.mall.order.dto.OrderItemDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderHistoryDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private boolean deliveryDone;
    private boolean paymentDone;
    private boolean refundChangeDone;
    private List<OrderItemDTO> items;
}