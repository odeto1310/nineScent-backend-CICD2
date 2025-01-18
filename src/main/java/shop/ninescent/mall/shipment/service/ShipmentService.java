package shop.ninescent.mall.shipment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.shipment.domain.Shipment;
import shop.ninescent.mall.shipment.dto.ShipmentDTO;
import shop.ninescent.mall.shipment.repository.ShipmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    // 여러 주문의 배송 정보 조회
    public List<ShipmentDTO> getShipmentsByOrderIds(List<Long> orderIds) {
        return shipmentRepository.findByOrderIdIn(orderIds).stream()
                .map(shipment -> new ShipmentDTO(
                        shipment.getShipmentId(),
                        shipment.getOrderId(),
                        shipment.getTrackingNum(),
                        shipment.getTrackingDate(),
                        shipment.getDeliveryStatus()
                ))
                .collect(Collectors.toList());
    }

    public ShipmentDTO getShipmentByOrderId(Long orderId) {
        Shipment shipment = shipmentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));

        return new ShipmentDTO(
                shipment.getShipmentId(),
                shipment.getOrderId(),
                shipment.getTrackingNum(),
                shipment.getTrackingDate(),
                shipment.getDeliveryStatus()
        );
    }
}