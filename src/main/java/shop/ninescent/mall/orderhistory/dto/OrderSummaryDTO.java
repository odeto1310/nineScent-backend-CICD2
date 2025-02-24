package shop.ninescent.mall.orderhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class OrderSummaryDTO {
    private long pendingPayment;  // 입금 대기 중
    private long preparingDelivery; // 배송 준비 중
//    private long shipping;  // 배송 중
    private long delivered; // 배송 완료
    private long canceled;  // 취소 / 반품 / 교환
}