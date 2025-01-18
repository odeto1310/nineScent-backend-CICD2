package shop.ninescent.mall.refundchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.refundchange.dto.RefundChangeDTO;
import shop.ninescent.mall.refundchange.service.RefundChangeService;

import java.util.List;

@RestController
@RequestMapping("/api/refund-change")
@RequiredArgsConstructor
public class RefundChangeController {

    private final RefundChangeService refundChangeService;

    // 주문 기준 환불/교환 조회
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<RefundChangeDTO>> getRefundChangeByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(refundChangeService.getRefundChangeByOrderId(orderId));
    }

    // 환불/교환 요청 등록
    @PostMapping("/create")
    public ResponseEntity<RefundChangeDTO> createRefundChange(@RequestBody RefundChangeDTO dto) {
        return ResponseEntity.ok(refundChangeService.createRefundChange(dto));
    }
}
