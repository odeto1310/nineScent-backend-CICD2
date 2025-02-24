package shop.ninescent.mall.orderhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.ninescent.mall.order.dto.OrderItemDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderHistoryDTO {
    private long orderId;
    private LocalDateTime orderDate;
    private String orderStatus;  // 주문 상태 (결제완료, 배송중 등)
    private long finalAmount; // 총 결제 금액
    private long shippingFee; // 배송비
    private List<OrderItemDTO> orderItems;
}