//package shop.ninescent.mall.orderhistory.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import shop.ninescent.mall.orderhistory.dto.OrderHistoryDTO;
//import shop.ninescent.mall.orderhistory.service.OrderHistoryService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/orderhist")
//@RequiredArgsConstructor
//public class OrderHistoryController {
//    private final OrderHistoryService orderHistoryService;
//
//    // 주문 내역 조회
//    @GetMapping("/{userNo}")
//    public ResponseEntity<List<OrderHistoryDTO>> getOrders(@PathVariable Long userNo) {
//        List<OrderHistoryDTO> orderHistory = orderHistoryService.getOrderHistory(userNo);
//        return ResponseEntity.ok(orderHistory);
//    }
//
////    // 최근 3개월 주문 내역 조회
////    @GetMapping("/recent/{userNo}")
////    public ResponseEntity<List<OrderHistDTO>> getRecentOrders(@PathVariable Long userNo) {
////        List<OrderHistDTO> orderHistory = orderHistService.getRecentOrderHistory(userNo);
////        return ResponseEntity.ok(orderHistory);
////    }
//
//}
//
