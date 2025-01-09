package shop.ninescent.mall.mypage.mapper;

import shop.ninescent.mall.mypage.dto.OrderDto;
import shop.ninescent.mall.mypage.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    // DTO → Entity 변환
    public Order toEntity(OrderDto dto) {
        Order entity = new Order();
        entity.setOrderId(dto.getOrderId());
        entity.setUserNo(dto.getUserNo());
        entity.setAddrNo(dto.getAddrNo());
        entity.setOrderDate(dto.getOrderDate());
        entity.setDeliveryDone(dto.getDeliveryDone());
        return entity;
    }

    // Entity → DTO 변환
    public OrderDto toDto(Order entity) {
        OrderDto dto = new OrderDto();
        dto.setOrderId(entity.getOrderId());
        dto.setUserNo(entity.getUserNo());
        dto.setAddrNo(entity.getAddrNo());
        dto.setOrderDate(entity.getOrderDate());
        dto.setDeliveryDone(entity.getDeliveryDone());
        return dto;
    }
}
