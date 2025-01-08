package shop.ninescent.mall.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.order.dto.OrderItemDTO;
import shop.ninescent.mall.order.dto.OrderPrepareRequestDTO;
import shop.ninescent.mall.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/prepare")
    //주문하기 페이지로 이동 (단일 or 장바구니)
    public ResponseEntity<?> prepareOrder(@RequestBody OrderPrepareRequestDTO request) {
        if (request.getCartId() != null) {
            // 장바구니 기반 주문하기 페이지
            List<OrderItemDTO> orderDetails = orderService.prepareCartOrder(request.getCartId(), request.getUserNo());
            return ResponseEntity.ok(orderDetails);
        } else if (request.getItemId() != null && request.getQuantity() != null) {
            // 단일 상품 기반 주문하기 페이지
            OrderItemDTO orderDetail = orderService.prepareSingleOrder(request.getItemId(), request.getQuantity(), request.getUserNo());
            return ResponseEntity.ok(orderDetail);
        } else {
            return ResponseEntity.badRequest().body("Invalidd request. ");
        }
    }
    // 주문 취소 (로그 기반 재고 복원)
    @PostMapping("/cancel")
        public ResponseEntity<String> cancelOrder(@RequestParam Long logId) {
        orderService.cancelStockLog(logId);
        return ResponseEntity.ok("Order canceled and stock restored successfully.");
    }
}
