package shop.ninescent.mall.orderhistory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.order.domain.Orders;
import shop.ninescent.mall.orderhistory.dto.OrderHistoryDTO;
import shop.ninescent.mall.orderhistory.dto.OrderSummaryDTO;
import shop.ninescent.mall.orderhistory.service.OrderHistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/orderhist")
@RequiredArgsConstructor
public class OrderHistoryController {
    private final OrderHistoryService orderHistoryService;

    // 주문 상태 요약 조회 API
    @GetMapping("/summary/{userNo}")
    public ResponseEntity<OrderSummaryDTO> getOrderSummary(@PathVariable Long userNo) {
        OrderSummaryDTO summary = orderHistoryService.getOrderSummary(userNo);
        return ResponseEntity.ok(summary);
    }

    // ✅ 최근 1개월 주문 내역 조회
    @GetMapping("/recent/{userNo}")
    public ResponseEntity<List<OrderHistoryDTO>> getRecentOrders(@PathVariable Long userNo) {
        List<OrderHistoryDTO> orders = orderHistoryService.getRecentOrders(userNo);
        return ResponseEntity.ok(orders);
    }

    // 전체 주문 내역 조회 (페이징)
    @GetMapping("/{userNo}")
    public ResponseEntity<Page<OrderHistoryDTO>> getAllOrders(
            @PathVariable Long userNo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<OrderHistoryDTO> orders = orderHistoryService.getAllOrders(userNo, page, size);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/detail/{orderId}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long orderId) {
        try {
            Orders order = orderHistoryService.getOrderDetail(orderId);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("주문을 찾을 수 없습니다.");
        }
    }
}

