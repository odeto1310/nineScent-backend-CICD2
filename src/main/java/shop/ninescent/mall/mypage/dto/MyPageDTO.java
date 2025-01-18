package shop.ninescent.mall.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.ninescent.mall.cartItem.dto.CartItemDTO;
import shop.ninescent.mall.item.dto.ItemDTO;
import shop.ninescent.mall.member.domain.UserVO;
import shop.ninescent.mall.orderhistory.dto.OrderHistoryDTO;
import shop.ninescent.mall.refundchange.dto.RefundChangeDTO;
import shop.ninescent.mall.shipment.dto.ShipmentDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPageDTO {
    private UserVO userInfo;                            // 사용자 정보
    private List<OrderHistoryDTO> recentOrders;          // 최근 주문 내역
    private List<ShipmentDTO> shipmentStatuses;         // 배송 상황
    private List<RefundChangeDTO> refundChangeHistories; // 환불/교환 내역
}
