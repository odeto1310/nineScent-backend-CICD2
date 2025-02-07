package shop.ninescent.mall.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.ninescent.mall.address.domain.Address;
import shop.ninescent.mall.address.repository.AddressRepository;
import shop.ninescent.mall.cartItem.domain.Cart;
import shop.ninescent.mall.cartItem.domain.CartItem;
import shop.ninescent.mall.cartItem.repository.CartRepository;
import shop.ninescent.mall.item.domain.Item;
import shop.ninescent.mall.item.repository.ItemRepository;
import shop.ninescent.mall.member.domain.User;
import shop.ninescent.mall.member.repository.UserRepository;
import shop.ninescent.mall.order.domain.OrderItems;
import shop.ninescent.mall.order.domain.Orders;
import shop.ninescent.mall.order.domain.StockLog;
import shop.ninescent.mall.order.dto.OrderItemDTO;
import shop.ninescent.mall.order.dto.OrderRequestDTO;
import shop.ninescent.mall.order.repository.OrderRepository;
import shop.ninescent.mall.order.repository.StockLogRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Orders createOrder(OrderRequestDTO orderRequest) {
        User user = userRepository.findById(orderRequest.getUserNo())
                .orElseThrow(() -> new RuntimeException("사용자 정보 없음"));
        Address address = addressRepository.findById(orderRequest.getAddressNo())
                .orElseThrow(() -> new RuntimeException("주소 정보 없음"));

        Orders order = new Orders();
        order.setUser(user);
        order.setAddress(address);
        order.setPaymentDone(orderRequest.isPaymentDone());
        order.setDeliveryDone(false); // 주문 시 배송 상태는 기본값
        order.setRefundChangeDone(false);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItems> orderItems = orderRequest.getOrderItems().stream()
                .map(itemDTO -> {
                    Item item = itemRepository.findById(itemDTO.getItemId())
                            .orElseThrow(() -> new RuntimeException("상품 정보 없음"));

                    OrderItems orderItem = new OrderItems();
                    orderItem.setOrder(order);
                    orderItem.setItem(item);
                    orderItem.setQuantity(itemDTO.getQuantity());
                    orderItem.setOriginalPrice(itemDTO.getOriginalPrice());
                    orderItem.setDiscountedPrice(itemDTO.getDiscountedPrice());

                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        return orderRepository.save(order);
    }

    public Orders getOrderDetail(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문 정보 없음"));
    }
}