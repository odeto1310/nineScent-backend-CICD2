package shop.ninescent.mall.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.order.domain.Orders;
import shop.ninescent.mall.order.dto.OrderItemDTO;
import shop.ninescent.mall.order.dto.OrderPrepareRequestDTO;
import shop.ninescent.mall.order.dto.OrderRequestDTO;
import shop.ninescent.mall.order.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Orders> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        Orders order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrderDetail(@PathVariable Long orderId) {
        Orders order = orderService.getOrderDetail(orderId);
        return ResponseEntity.ok(order);
    }
}