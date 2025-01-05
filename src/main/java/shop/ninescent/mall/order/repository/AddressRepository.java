package shop.ninescent.mall.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.order.domain.Address;


public interface AddressRepository extends JpaRepository<Address, Long> {
}

