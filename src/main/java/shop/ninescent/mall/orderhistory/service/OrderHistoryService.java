package shop.ninescent.mall.orderhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.order.domain.OrderItems;
import shop.ninescent.mall.order.domain.Orders;
import shop.ninescent.mall.order.dto.OrderItemDTO;
import shop.ninescent.mall.order.repository.OrderRepository;
import shop.ninescent.mall.orderhistory.dto.OrderHistoryDTO;
import shop.ninescent.mall.orderhistory.dto.OrderSummaryDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {
    private final OrderRepository orderRepository;

    // 주문 상태별 개수 조회 (최근 1개월 내)
    public OrderSummaryDTO getOrderSummary(Long userNo) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        long pendingPayment = orderRepository.countByUser_UserNoAndPaymentDoneFalseAndOrderDateAfter(userNo, oneMonthAgo);
        long preparingDelivery = orderRepository.countByUser_UserNoAndDeliveryDoneFalseAndPaymentDoneTrueAndOrderDateAfter(userNo, oneMonthAgo);
//        long shipping = orderRepository.countByUser_UserNoAndDeliveryDoneFalseAndOrderDateAfter(userNo, oneMonthAgo);
        long delivered = orderRepository.countByUser_UserNoAndDeliveryDoneTrueAndOrderDateAfter(userNo, oneMonthAgo);
        long canceled = orderRepository.countByUser_UserNoAndRefundChangeDoneTrueAndOrderDateAfter(userNo, oneMonthAgo);

        return new OrderSummaryDTO(pendingPayment, preparingDelivery, delivered, canceled);
    }

    // 최근 1개월 내 주문 내역 조회
    public List<OrderHistoryDTO> getRecentOrders(Long userNo) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "orderId"));
        List<Orders> orders = orderRepository.findByUser_UserNoAndOrderDateAfter(userNo, oneMonthAgo, pageRequest);
        return orders.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // 전체 주문 내역 조회 (페이징 적용)
    public Page<OrderHistoryDTO> getAllOrders(Long userNo, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Orders> orderPage = orderRepository.findByUser_UserNo_OrderByOrderIdDesc(userNo, pageable);

        return orderPage.map(this::convertToDto);
    }

    // 주문 엔티티를 DTO로 변환
    private OrderHistoryDTO convertToDto(Orders order) {
        OrderHistoryDTO dto = new OrderHistoryDTO();
        dto.setOrderId(order.getOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(determineOrderStatus(order));
        dto.setFinalAmount(order.getFinalAmount());
        dto.setShippingFee(order.getShippingFee());
        dto.setOrderItems(order.getOrderItems().stream().map(this::convertItemToDto).collect(Collectors.toList()));
        return dto;
    }

    private OrderItemDTO convertItemToDto(OrderItems orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setOrderItemId(orderItem.getOrderItemId());
        dto.setItemName(orderItem.getItem().getItemName());
        dto.setItemId(orderItem.getItem().getItemId());
        dto.setMainPhoto(orderItem.getItem().getMainPhoto());
        dto.setQuantity(orderItem.getQuantity());
        dto.setOriginalPrice(orderItem.getOriginalPrice());
        dto.setDiscountedPrice(orderItem.getDiscountedPrice());
        return dto;
    }

    // 주문 상태 결정 로직 (비즈니스 로직에 맞게 수정 가능)
    private String determineOrderStatus(Orders order) {
        if (!order.isPaymentDone()) return "입금대기중";
        if (order.isPaymentDone() && !order.isDeliveryDone()) return "배송준비중";
//        if (order.isPaymentDone() && !order.isDeliveryDone()) return "배송중";
        if (order.isPaymentDone() && order.isDeliveryDone()) return "배송완료";
        if (order.isRefundChangeDone()) return "취소/반품/교환";
        return "구매확정";
    }

    // 주문 내역 상세보기
    public Orders getOrderDetail(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문 정보 없음"));
    }
}