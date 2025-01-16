package shop.ninescent.mall.mypage.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderDto {
    private Long orderId;
    private Long userNo;           // 사용자 번호
    private Long addrNo;           // 주소 번호
    private java.util.Date orderDate;  // 주문 날짜
    private Boolean deliveryDone;  // 배송 완료 여부
    private String deliveryStatus;
    private String requestType; // Cancel, Refund, Exchange
    private String reason;


}