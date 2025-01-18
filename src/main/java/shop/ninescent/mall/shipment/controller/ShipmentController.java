package shop.ninescent.mall.shipment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.ninescent.mall.shipment.dto.ShipmentDTO;
import shop.ninescent.mall.shipment.service.ShipmentService;

import java.util.List;

@RestController
@RequestMapping("/api/shipment")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ShipmentDTO> getShipmentByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(shipmentService.getShipmentByOrderId(orderId));
    }
}