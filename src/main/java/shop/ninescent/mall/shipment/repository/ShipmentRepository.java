package shop.ninescent.mall.shipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ninescent.mall.shipment.domain.Shipment;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByOrderIdIn(List<Long> orderIds);
    Optional<Shipment> findByOrderId(Long orderId);

}