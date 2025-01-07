package shop.ninescent.mall.order.dto;

import lombok.Data;

@Data
public class OrderPrepareRequestDTO {
    private Long userNo;
    private Long cartId; //장바구니 기반 주문
    private Long itemId; //단일 상품 주문
    private Integer quantity; //단일 상품 수량
    private Long addrNo;  // 배송지 정보
}
