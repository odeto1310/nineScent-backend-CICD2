package shop.ninescent.mall.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Long userNo;  // 사용자 ID
    private Long addressNo;  // 배송지 ID
    private boolean paymentDone;  // 결제 완료 여부
    private List<OrderItemDTO> orderItems;  // 주문 아이템 목록
    private long totalPrice; // 원래 가격 총합
    private long totalDiscount; // 총 할인 금액
    private long finalAmount; // 총 결제 금액 (상품 금액 - 할인 + 배송비)
    private long shippingFee; // 배송비 정보

}
