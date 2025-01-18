package shop.ninescent.mall.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.payment.domain.Payment;
import shop.ninescent.mall.payment.repository.PaymentRepository;
import shop.ninescent.mall.payment.dto.PaymentDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentDTO getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        return new PaymentDTO(
                payment.getPaymentId(),
                payment.getOrderId(),
                payment.getPaymentStatus(),
                payment.getPaymentMethod(),
                payment.getPaymentDate(),
                payment.getTotalAmount()
        );
    }
}
