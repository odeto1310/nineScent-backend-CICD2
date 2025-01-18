package shop.ninescent.mall.refundchange.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refund_change")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refundId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long paymentId; // 결제 정보와 연결

    @Column(nullable = false)
    private Long shipmentId; // 배송 정보와 연결

    @Column(nullable = false)
    private Long businessAddressId; // 환불/교환 사업장 배송 주소 ID

    @Column(nullable = false)
    private Long deliveryAddrId; // 교환 시 보내야 할 주소 ID (Address 테이블 연동)

    @Column(nullable = false)
    private Long refundChangeType; // 1: 환불, 2: 교환

    private String refundMethod; // 환불 방법 (계좌이체, 카드 취소 등)

    private String reasonCategory; // 사유 카테고리

    private String reasonText; // 상세 사유

    private Boolean isDone; // 처리 여부

    private LocalDateTime updateDate;
}