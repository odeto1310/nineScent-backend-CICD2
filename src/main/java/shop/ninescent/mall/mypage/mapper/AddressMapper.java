package shop.ninescent.mall.mypage.mapper;

import shop.ninescent.mall.mypage.dto.AddressDto;
import shop.ninescent.mall.mypage.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    // DTO → Entity 변환
    public Address toEntity(AddressDto dto) {
        Address entity = new Address();
        entity.setAddrNo(dto.getAddrNo());
        entity.setUserNo(dto.getUserNo());
        entity.setAddrName(dto.getAddrName());
        entity.setAddrContact(dto.getAddrContact());
        entity.setAddrZipcode(dto.getAddrZipcode());
        entity.setAddrAddress(dto.getAddrAddress());
        entity.setAddrDetail(dto.getAddrDetail());
        entity.setAddrRequest(dto.getAddrRequest());
        entity.setIsDefault(dto.getIsDefault());
        entity.setIsLiked(dto.getIsLiked());
        return entity;
    }

    // Entity → DTO 변환
    public AddressDto toDto(Address entity) {
        AddressDto dto = new AddressDto();
        dto.setAddrNo(entity.getAddrNo());
        dto.setUserNo(entity.getUserNo());
        dto.setAddrName(entity.getAddrName());
        dto.setAddrContact(entity.getAddrContact());
        dto.setAddrZipcode(entity.getAddrZipcode());
        dto.setAddrAddress(entity.getAddrAddress());
        dto.setAddrDetail(entity.getAddrDetail());
        dto.setAddrRequest(entity.getAddrRequest());
        dto.setIsDefault(entity.getIsDefault());
        dto.setIsLiked(entity.getIsLiked());
        return dto;
    }
}

