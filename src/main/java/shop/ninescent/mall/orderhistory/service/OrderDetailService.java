//package shop.ninescent.mall.orderhistory.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import shop.ninescent.mall.order.domain.Orders;
//import shop.ninescent.mall.order.repository.OrderRepository;
//import shop.ninescent.mall.orderhistory.dto.*;
//import shop.ninescent.mall.orderhistory.repository.PaymentRepository;
//import shop.ninescent.mall.orderhistory.repository.ShipmentRepository;
//
//import java.util.ArrayList;
//
//@Service
//@RequiredArgsConstructor
//public class OrderDetailService {
//
//    private final OrderRepository orderRepository;
//    private final ShipmentRepository shipmentRepository;
//    private final PaymentRepository paymentRepository;
//    private final RefundChangeRepository refundChangeRepository;
//
//    // 주문 상세 조회 (배송, 결제, 취소/환불 포함)
//    public OrderDetailDTO getOrderDetail(Long orderId) {
//        // 주문 정보 조회
//        Orders order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
//
//        OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO(
//                order.getOrderId(),
//                order.getOrderDate(),
//                order.isDeliveryDone(),
//                order.isPaymentDone(),
//                order.isRefundChangeDone(),
//                new ArrayList<>()
//        );
//
//        // 배송 정보 조회
//        Shipment shipment = shipmentRepository.findByOrderId(orderId)
//                .orElse(null);
//        ShipmentDTO shipmentDTO = (shipment != null) ? new ShipmentDTO(
//                shipment.getShipmentId(),
//                shipment.getOrderId(),
//                shipment.getTrackingNum(),
//                shipment.getTrackingDate(),
//                shipment.getDeliveryStatus()
//        ) : null;
//
//        // 결제 정보 조회
//        Payment payment = paymentRepository.findByOrderId(orderId)
//                .orElse(null);
//        PaymentDTO paymentDTO = (payment != null) ? new PaymentDTO(
//                payment.getPaymentId(),
//                payment.getOrderId(),
//                payment.getPaymentStatus(),
//                payment.getPaymentMethod(),
//                payment.getPaymentDate(),
//                payment.getTotalAmount()
//        ) : null;
//
//        // 취소/환불 정보 조회
//        RefundChange refundChange = refundChangeRepository.findByOrderId(orderId)
//                .orElse(null);
//        RefundChangeDTO refundChangeDTO = (refundChange != null) ? new RefundChangeDTO(
//                refundChange.getRefundId(),
//                refundChange.getOrderId(),
//                String.valueOf(refundChange.getDeliveryContact()),
//                refundChange.getDeliveryName(),
//                String.valueOf(refundChange.getDeliveryZipcode()),
//                refundChange.getDeliveryAddr(),
//                refundChange.getDeliveryAddrDetail(),
//                refundChange.getRefundChangeType(),
//                refundChange.getRefundMethod(),
//                refundChange.getTrackingNum(),
//                refundChange.getReasonCategory(),
//                refundChange.getReasonText(),
//                refundChange.getIsDone(),
//                refundChange.getUpdateDate()
//        ) : null;
//
//        return OrderDetailDTO.builder()
//                .orderInfo(orderHistoryDTO)
//                .shipmentInfo(shipmentDTO)
//                .paymentInfo(paymentDTO)
//                .refundChangeInfo(refundChangeDTO)
//                .build();
//    }
//}
