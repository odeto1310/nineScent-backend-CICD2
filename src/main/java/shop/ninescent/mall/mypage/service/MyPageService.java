//package shop.ninescent.mall.mypage.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import shop.ninescent.mall.member.domain.UserVO;
//import shop.ninescent.mall.member.service.UserService;
//import shop.ninescent.mall.mypage.dto.MyPageDTO;
//import shop.ninescent.mall.orderhistory.dto.OrderHistoryDTO;
//import shop.ninescent.mall.orderhistory.service.OrderHistoryService;
//import shop.ninescent.mall.refundchange.dto.RefundChangeDTO;
//import shop.ninescent.mall.refundchange.service.RefundChangeService;
//import shop.ninescent.mall.shipment.dto.ShipmentDTO;
//import shop.ninescent.mall.shipment.service.ShipmentService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class MyPageService {
//
//    private final UserService userService;
//    private final OrderHistoryService orderHistoryService;
//    private final ShipmentService shipmentService;
//    private final RefundChangeService refundChangeService;
//
//    // 마이페이지 통합 조회
//    public MyPageDTO getMyPageOverview(Long userNo) {
//        // 사용자 정보
//        UserVO userInfo = userService.getInfo(userNo);
//
//        // 최근 주문 내역
//        List<OrderHistoryDTO> recentOrders = orderHistoryService.getOrderHistory(userNo);
//
//        // 주문 ID 리스트로 배송/환불 정보 조회
//        List<Long> orderIds = recentOrders.stream().map(OrderHistoryDTO::getOrderId).collect(Collectors.toList());
//
//        // 주문별 배송 상태 조회
//        List<ShipmentDTO> shipmentStatuses = shipmentService.getShipmentsByOrderIds(orderIds);
//
//        // 주문별 환불/교환 내역 조회
//        List<RefundChangeDTO> refundChangeHistories = refundChangeService.getRefundChangesByOrderIds(orderIds);
//
//        return MyPageDTO.builder()
//                .userInfo(userInfo)
//                .recentOrders(recentOrders).
//                shipmentStatuses(shipmentStatuses).
//                refundChangeHistories(refundChangeHistories)
//                .build();
//    }
//}