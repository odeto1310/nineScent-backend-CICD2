package shop.ninescent.mall.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ninescent.mall.address.domain.ShipmentExtra;

@Repository
public interface ShipmentExtraRepository extends JpaRepository<ShipmentExtra, Long> {
    boolean existsByExtraZipcode(String extraZipcode);
}