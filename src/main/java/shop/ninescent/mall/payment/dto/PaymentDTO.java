package shop.ninescent.mall.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long paymentId;
    private Long orderId;
    private String paymentStatus;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private Long totalAmount;
}