package shop.ninescent.mall.address.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "shipment_extra")
public class ShipmentExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long extraId;
    @Column(nullable = false)
    private String extraZipcode;
}