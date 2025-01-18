package shop.ninescent.mall.orderhistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.address.domain.Address;
import shop.ninescent.mall.address.repository.AddressRepository;
import shop.ninescent.mall.item.domain.Item;
import shop.ninescent.mall.item.repository.ItemRepository;
import shop.ninescent.mall.order.domain.OrderItems;
import shop.ninescent.mall.order.domain.Orders;
import shop.ninescent.mall.order.dto.OrderItemDTO;
import shop.ninescent.mall.order.repository.OrderItemRepository;
import shop.ninescent.mall.order.repository.OrderRepository;
import shop.ninescent.mall.orderhistory.dto.OrderHistoryDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final AddressRepository addressRepository;

    public List<OrderHistoryDTO> getOrderHistory(Long userNo) {
        // 최근 3개월 주문 내역 조회 하려했으나 일단 다 가져오자~,
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

        List<Orders> recentOrders = orderRepository.findByUserNo(userNo);

        return recentOrders.stream().map(order -> {
            List<OrderItems> orderItems = orderItemRepository.findByOrderId(order.getOrderId());

            List<OrderItemDTO> itemDTOs = orderItems.stream().map(orderItem -> {
                Item item = itemRepository.findById(orderItem.getItemId())
                        .orElseThrow(() -> new IllegalArgumentException("Item not found"));
                Address address = addressRepository.findById(order.getAddrNo())
                        .orElseThrow(() -> new IllegalArgumentException("Address not found"));

                return new OrderItemDTO(
                        item.getItemId(),
                        item.getItemName(),
                        orderItem.getQuantity(),
                        item.getPrice(),
                        item.getDiscountedPrice(),
                        address,
                        address.getIsExtraFee() ? 5000L : 0L
                );
            }).collect(Collectors.toList());

            return new OrderHistoryDTO(
                    order.getOrderId(),
                    order.getOrderDate(),
                    order.isDeliveryDone(),
                    order.isPaymentDone(),
                    order.isRefundChangeDone(),
                    itemDTOs
            );
        }).collect(Collectors.toList());
    }
}
