package shop.ninescent.mall.orderhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.ninescent.mall.refundchange.dto.RefundChangeDTO;
import shop.ninescent.mall.shipment.dto.ShipmentDTO;
import shop.ninescent.mall.payment.dto.PaymentDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {
    private OrderHistoryDTO orderInfo;
    private ShipmentDTO shipmentInfo;
    private PaymentDTO paymentInfo;
    private RefundChangeDTO refundChangeInfo;
}
