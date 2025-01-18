package shop.ninescent.mall.refundchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.refundchange.domain.RefundChange;
import shop.ninescent.mall.refundchange.dto.RefundChangeDTO;
import shop.ninescent.mall.refundchange.repository.RefundChangeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class RefundChangeService {

    private final RefundChangeRepository refundChangeRepository;

    // 여러 주문의 환불/교환 정보 조회
    public List<RefundChangeDTO> getRefundChangesByOrderIds(List<Long> orderIds) {
        return refundChangeRepository.findByOrderIdIn(orderIds).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 환불/교환 요청 조회 (주문 기준)
    public List<RefundChangeDTO> getRefundChangeByOrderId(Long orderId) {
        return refundChangeRepository.findByOrderId(orderId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 환불/교환 요청 등록
    public RefundChangeDTO createRefundChange(RefundChangeDTO dto) {
        RefundChange refundChange = convertToEntity(dto);
        refundChange.setIsDone(false);
        refundChange.setUpdateDate(LocalDateTime.now());
        RefundChange saved = refundChangeRepository.save(refundChange);
        return convertToDTO(saved);
    }

    // Entity -> DTO 변환
    private RefundChangeDTO convertToDTO(RefundChange entity) {
        return new RefundChangeDTO(
                entity.getRefundId(),
                entity.getOrderId(),
                entity.getPaymentId(),
                entity.getShipmentId(),
                entity.getBusinessAddressId(),
                entity.getDeliveryAddrId(),
                entity.getRefundChangeType(),
                entity.getRefundMethod(),
                entity.getReasonCategory(),
                entity.getReasonText(),
                entity.getIsDone(),
                entity.getUpdateDate()
        );
    }

    // DTO -> Entity 변환
    private RefundChange convertToEntity(RefundChangeDTO dto) {
        return new RefundChange(
                dto.getRefundId(),
                dto.getOrderId(),
                dto.getPaymentId(),
                dto.getShipmentId(),
                dto.getBusinessAddressId(),
                dto.getDeliveryAddrId(),
                dto.getRefundChangeType(),
                dto.getRefundMethod(),
                dto.getReasonCategory(),
                dto.getReasonText(),
                dto.getIsDone(),
                dto.getUpdateDate()
        );
    }
}
