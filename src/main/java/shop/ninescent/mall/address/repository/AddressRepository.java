package shop.ninescent.mall.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.address.domain.Address;

import java.util.List;
import java.util.Optional;


public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByUserNoAndIsDefaultTrue(Long userNo);
    Address findByAddrNoAndUserNo(Long addrNo, Long userNo);
    List<Address> findAllByUserNo(Long userNo);
}

