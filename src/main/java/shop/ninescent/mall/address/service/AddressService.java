package shop.ninescent.mall.address.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.address.dto.AddressDTO;
import shop.ninescent.mall.address.domain.Address;
import shop.ninescent.mall.address.repository.AddressRepository;
import shop.ninescent.mall.address.repository.ShipmentExtraRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final ShipmentExtraRepository shipmentExtraRepository;

    public Address addAddress(Long userNo, AddressDTO addressDTO) {
        boolean isExtraFee = shipmentExtraRepository.existsByExtraZipcode(String.valueOf(addressDTO.getAddrZipcode()));

        Address address = new Address();
        address.setUserNo(userNo);
        address.setAddrName(addressDTO.getAddrName());
        address.setAddrContact(addressDTO.getAddrContact());
        address.setAddrZipcode(String.valueOf(addressDTO.getAddrZipcode()));
        address.setAddrAddress(addressDTO.getAddrAddress());
        address.setAddrDetail(addressDTO.getAddrDetail());
        address.setAddrRequest(addressDTO.getAddrRequest());
        address.setIsDefault(false);
        address.setIsLiked(false);
        address.setIsExtraFee(isExtraFee);


        return addressRepository.save(address);
    }

    // 기본 주소 가져오기
    public Address getDefaultAddress(Long userNo) {
        return addressRepository.findByUserNoAndIsDefaultTrue(userNo)
                .orElseThrow(() -> new IllegalArgumentException("Default address not found"));
    }

    // 모든 주소 가져오기
    public List<Address> getAddresses(Long userNo) {
        return addressRepository.findAllByUserNo(userNo);
    }

//    // 특정 주소 가져오기
//    public Address getAddressByIdAndUserNo(Long addrNo, Long userNo) {
//        return addressRepository.findByAddrNoAndUserNo(addrNo, userNo)
//                .orElseThrow(() -> new IllegalArgumentException("Address not found for user"));
//    }
}