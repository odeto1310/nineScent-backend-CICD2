package shop.ninescent.mall.refundchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ninescent.mall.refundchange.domain.RefundChange;
import shop.ninescent.mall.shipment.domain.Shipment;

import java.util.List;
import java.util.Optional;


@Repository
public interface RefundChangeRepository extends JpaRepository<RefundChange, Long> {
    List<RefundChange> findByOrderIdIn(List<Long> orderIds);
    List<RefundChange> findByOrderId(Long orderId);

}