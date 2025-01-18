package shop.ninescent.mall.address.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.address.dto.AddressDTO;
import shop.ninescent.mall.address.domain.Address;
import shop.ninescent.mall.address.repository.AddressRepository;
import shop.ninescent.mall.address.repository.ShipmentExtraRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final ShipmentExtraRepository shipmentExtraRepository;

    public Address addAddress(Long userNo, AddressDTO addressDTO) {
        Address address = new Address();
        address.setUserNo(userNo);
        address.setAddrName(addressDTO.getAddrName());
        address.setAddrContact(addressDTO.getAddrContact());
        address.setAddrZipcode(addressDTO.getAddrZipcode());
        address.setAddrAddress(addressDTO.getAddrAddress());
        address.setAddrDetail(addressDTO.getAddrDetail());
        address.setAddrRequest(addressDTO.getAddrRequest());
        address.setIsDefault(false);
        address.setIsLiked(false);
        // extraFee확인
        boolean isExtraFee = shipmentExtraRepository.existsByExtraZipcode(String.valueOf(addressDTO.getAddrZipcode()));
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

    public void updateAddress(Long addrNo, AddressDTO addressDTO) {
        Address address = addressRepository.findById(addrNo)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        address.setAddrName(addressDTO.getAddrName());
        address.setAddrContact(addressDTO.getAddrContact());
        address.setAddrZipcode(addressDTO.getAddrZipcode());
        address.setAddrAddress(addressDTO.getAddrAddress());
        address.setAddrDetail(addressDTO.getAddrDetail());
        address.setAddrRequest(addressDTO.getAddrRequest());

        // extraFee확인
        boolean isExtraFee = shipmentExtraRepository.existsByExtraZipcode(String.valueOf(addressDTO.getAddrZipcode()));
        address.setIsExtraFee(isExtraFee);
        addressRepository.save(address);
    }

    public void deleteAddress(Long addrNo) {
        Address address = addressRepository.findById(addrNo)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        // default 이외 제거
        if(address.getIsDefault()) {
            throw new IllegalStateException("Default address cannot be deleted.");
        }
        addressRepository.delete(address);
    }

    // Liked값 변경
    public void setLikedAddress(Long addrNo) {
        Address address = addressRepository.findById(addrNo)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        // isLiked 값을 반전(true → false, false → true)
        address.setIsLiked(!address.getIsLiked());
        addressRepository.save(address);
    }
    // Default값 변경
    public void setDefaultAddress(Long addrNo, Long userNo) {
        List<Address> userAddresses = addressRepository.findAllByUserNo(userNo);

        // 모든 주소의 isDefault를 false로 설정
        userAddresses.forEach(address -> {
            if(address.getIsDefault()) {
                address.setIsDefault(false);
                addressRepository.save(address);
            }
        });

        // 선택된 주소를 default true로 변경
        Address selectedAddress = addressRepository.findById(addrNo)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        selectedAddress.setIsDefault(true);
        addressRepository.save(selectedAddress);

    }
}