package shop.ninescent.mall.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
import shop.ninescent.mall.order.repository.OrderRepository;
import shop.ninescent.mall.order.repository.StockLogRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;
    private final AddressRepository addressRepository;
    private final StockLogRepository stockLogRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    // 단일 상품 주문 준비
    public OrderItemDTO prepareSingleOrder(Long itemId, Integer quantity, Long userNo, Long selectedAddrNo) {
        Address address = (selectedAddrNo != null) ? addressRepository.findById(selectedAddrNo).orElseThrow(() -> new IllegalArgumentException("Selected address not found")) : addressRepository.findByUserUserNoAndIsDefaultTrue(userNo).orElseThrow(() -> new IllegalArgumentException("Default address not found"));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Invalid itemId"));
        User user = userRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException("User not found: " + userNo));

        // 재고 감소
        adjustStock(item, quantity, "REDUCE");

        // 주문 생성
        Orders order = new Orders();
        order.setUser(user);
        order.setAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setDeliveryDone(false);
        order.setPaymentDone(false);
        order.setRefundChangeDone(false);

        OrderItems orderItem = new OrderItems();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItem.setQuantity(quantity);
        orderItem.setOriginalPrice(item.getPrice());
        orderItem.setDiscountedPrice(item.getDiscountedPrice() != null ? item.getDiscountedPrice() : item.getPrice());
        order.getOrderItems().add(orderItem);

        // Save order
        orderRepository.save(order);

        Long shippingFee = address.getIsExtraFee() ? 5000L : 0L;

        return new OrderItemDTO(item.getItemId(), item.getItemName(), quantity, item.getPrice(), item.getDiscountedPrice(), user.getUserNo(), user.getName(), address.getAddrNo(), address.getAddrDetail(), shippingFee);
    }


    // 장바구니 기반 주문
    public List<OrderItemDTO> prepareCartOrder(Long userNo, Long selectedAddrNo) {
        Address address = (selectedAddrNo != null) ? addressRepository.findById(selectedAddrNo).orElseThrow(() -> new IllegalArgumentException("Selected address not found")) : addressRepository.findByUserUserNoAndIsDefaultTrue(userNo).orElseThrow(() -> new IllegalArgumentException("Default address not found"));
        Cart cart = cartRepository.findByUserUserNo(userNo).orElseThrow(() -> new IllegalArgumentException("Cart not found for user"));
        User user = userRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException("User not found: " + userNo));

        Orders order = new Orders();
        order.setUser(user);
        order.setAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setDeliveryDone(false);
        order.setPaymentDone(false);
        order.setRefundChangeDone(false);

        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            Item item = cartItem.getItem();
            adjustStock(item, cartItem.getQuantity(), "REDUCE");
            Long shippingFee = address.getIsExtraFee() ? 5000L : 0L;

            orderItemDTOs.add(new OrderItemDTO(item.getItemId(), item.getItemName(), cartItem.getQuantity(), item.getPrice(), item.getDiscountedPrice(), user.getUserNo(), user.getName(), address.getAddrNo(), address.getAddrDetail(), shippingFee));
        }

        return orderItemDTOs;
    }

    // 재고 조정
    private void adjustStock(Item item, Integer quantity, String changeType) {
        if ("REDUCE".equals(changeType)) {
            if (item.getStock() < quantity) {
                throw new IllegalStateException("Insufficient stock for item: " + item.getItemName());
            }
            item.setStock(item.getStock() - quantity);
        } else if ("RESTORE".equals(changeType)) {
            item.setStock(item.getStock() + quantity);
        }

        itemRepository.save(item);
        stockLogRepository.save(new StockLog(item, quantity, changeType));

        // 5분 후 재고 복원 예약
        if ("REDUCE".equals(changeType)) {
            executorService.schedule(() -> restoreStockFromLog(item.getItemId()), 5, TimeUnit.MINUTES);
        }
    }

    // 재고 복원 (타임아웃 기반)
    private void restoreStockFromLog(Long itemId) {
        StockLog stockLog = stockLogRepository.findTopByItemItemIdAndChangeTypeOrderByCreatedAtDesc(itemId, "REDUCE").orElseThrow(() -> new IllegalStateException("No stock log found for itemId: " + itemId));
        adjustStock(stockLog.getItem(), stockLog.getQuantity(), "RESTORE");
        stockLogRepository.delete(stockLog);
    }

    // 주문 취소 (로그 기반)
    public void cancelStockLog(Long logId) {
        StockLog stockLog = stockLogRepository.findById(logId).orElseThrow(() -> new IllegalArgumentException("Invalid logId"));

        adjustStock(stockLog.getItem(), stockLog.getQuantity(), "RESTORE");
        stockLogRepository.delete(stockLog);
    }
}