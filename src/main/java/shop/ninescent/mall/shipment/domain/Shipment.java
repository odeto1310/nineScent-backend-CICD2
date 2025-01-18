package shop.ninescent.mall.shipment.domain;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long userNo;

    private String trackingNum;

    private LocalDateTime trackingDate;

    private String deliveryStatus;
}