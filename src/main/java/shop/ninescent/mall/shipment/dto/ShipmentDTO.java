package shop.ninescent.mall.shipment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentDTO {
    private Long shipmentId;
    private Long orderId;
    private String trackingNum;
    private LocalDateTime trackingDate;
    private String deliveryStatus;
}