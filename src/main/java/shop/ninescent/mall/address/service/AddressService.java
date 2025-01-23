package shop.ninescent.mall.address.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.address.dto.AddressDTO;
import shop.ninescent.mall.address.domain.Address;
import shop.ninescent.mall.address.repository.AddressRepository;
import shop.ninescent.mall.address.repository.ShipmentExtraRepository;
import shop.ninescent.mall.member.domain.User;
import shop.ninescent.mall.member.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ShipmentExtraRepository shipmentExtraRepository;

    public Address addAddress(Long userNo, AddressDTO addressDTO) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userNo));

        Address address = Address.builder()
                .user(user)
                .addrName(addressDTO.getAddrName())
                .addrContact(addressDTO.getAddrContact())
                .addrZipcode(addressDTO.getAddrZipcode())
                .addrAddress(addressDTO.getAddrAddress())
                .addrDetail(addressDTO.getAddrDetail())
                .addrRequest(addressDTO.getAddrRequest())
                .isDefault(true)
                .isLiked(false)
                .isExtraFee(shipmentExtraRepository.existsByExtraZipcode(String.valueOf(addressDTO.getAddrZipcode())))
                .build();
        return addressRepository.save(address);
    }

    // 기본 주소 가져오기
    public Address getDefaultAddress(Long userNo) {
        return addressRepository.findByUserUserNoAndIsDefaultTrue(userNo)
                .orElseThrow(() -> new IllegalArgumentException("Default address not found"));
    }

    public List<AddressDTO> getAddresses(Long userNo) {
        return addressRepository.findAllByUserUserNo(userNo).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 주소 수정
    public void updateAddress(Long addrNo, AddressDTO addressDTO) {
        Address address = addressRepository.findById(addrNo)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        address.setAddrName(addressDTO.getAddrName());
        address.setAddrContact(addressDTO.getAddrContact());
        address.setAddrZipcode(addressDTO.getAddrZipcode());
        address.setAddrAddress(addressDTO.getAddrAddress());
        address.setAddrDetail(addressDTO.getAddrDetail());
        address.setAddrRequest(addressDTO.getAddrRequest());
        address.setIsExtraFee(shipmentExtraRepository.existsByExtraZipcode(String.valueOf(addressDTO.getAddrZipcode())));

        addressRepository.save(address);
    }


    public void deleteAddress(Long addrNo) {
        Address address = addressRepository.findByAddrNo(addrNo)
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
        List<Address> userAddresses = addressRepository.findAllByUserUserNo(userNo);

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

    public AddressDTO convertToDTO(Address address) {
        return AddressDTO.builder()
                .addrNo(address.getAddrNo())
                .addrName(address.getAddrName())
                .addrContact(address.getAddrContact())
                .addrZipcode(address.getAddrZipcode())
                .addrAddress(address.getAddrAddress())
                .addrDetail(address.getAddrDetail())
                .addrRequest(address.getAddrRequest())
                .isDefault(address.getIsDefault())
                .isLiked(address.getIsLiked())
                .isExtraFee(address.getIsExtraFee())
                .build();
    }
}