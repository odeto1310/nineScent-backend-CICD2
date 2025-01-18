package shop.ninescent.mall.refundchange.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundChangeDTO {
    private Long refundId;
    private Long orderId;
    private Long paymentId;
    private Long shipmentId;
    private Long businessAddressId;
    private Long deliveryAddrId;
    private Long refundChangeType;
    private String refundMethod;
    private String reasonCategory;
    private String reasonText;
    private Boolean isDone;
    private LocalDateTime updateDate;
}
