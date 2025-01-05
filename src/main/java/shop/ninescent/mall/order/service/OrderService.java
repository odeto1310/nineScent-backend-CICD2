package shop.ninescent.mall.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.cartItem.domain.CartItem;
import shop.ninescent.mall.cartItem.repository.CartItemRepository;
import shop.ninescent.mall.order.domain.Item;
import shop.ninescent.mall.order.domain.Address;
import shop.ninescent.mall.order.domain.OrderItems;
import shop.ninescent.mall.order.domain.StockLog;
import shop.ninescent.mall.order.dto.OrderItemDTO;
import shop.ninescent.mall.order.repository.AddressRepository;
import shop.ninescent.mall.order.repository.ItemRepository;
import shop.ninescent.mall.order.repository.OrderItemRepository;
import shop.ninescent.mall.order.repository.StockLogRepository;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final AddressRepository addressRepository;
    private final StockLogRepository stockLogRepository;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    // 단일 상품 주문 준비
    public OrderItemDTO prepareSingleOrder(Long itemId, Integer quantity, Long addrNo) {
        // stock 관리
        adjustStock(itemId, quantity, "REDUCE");

        Address address = addressRepository.findById(addrNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid addressId"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid itemId"));

        return new OrderItemDTO(
                item.getItemId(),
                item.getItemName(),
                quantity,
                item.getPrice(),
                item.getDiscountedPrice(),
                address
        );
    }

    // 장바구니 기반 주문
    public List<OrderItemDTO> prepareCartOrder(Long cartId, Long addrNo) {
        Address address = addressRepository.findById(addrNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid addressId"));

        List<CartItem> cartItems = cartItemRepository.findByCartIdAndIsSelectedTrue(cartId);

        // stock 관리
        for (CartItem cartItem : cartItems) {
            adjustStock(cartItem.getItemId(), cartItem.getQuantity(), "REDUCE");
        }

        return cartItems.stream()
                .map(cartItem -> {
                    Item item = itemRepository.findById(cartItem.getItemId())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid itemId"));
                    return new OrderItemDTO(
                            item.getItemId(),
                            item.getItemName(),
                            cartItem.getQuantity(),
                            item.getPrice(),
                            item.getDiscountedPrice(),
                            address
                    );
                })
                .collect(Collectors.toList());
    }
    // 재고 조정
    private void adjustStock(Long itemId, Integer quantity, String changeType) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid itemId"));

        if ("REDUCE".equals(changeType)) {
            if (item.getStock() < quantity) {
                throw new IllegalStateException("Insufficient stock for item: " + item.getItemName());
            }
            item.setStock(item.getStock() - quantity);
        } else if ("RESTORE".equals(changeType)) {
            item.setStock(item.getStock() + quantity);
        }

        itemRepository.save(item);
        stockLogRepository.save(new StockLog(itemId, quantity, changeType));

        // Schedule automatic stock restoration after 30 minutes
        if ("REDUCE".equals(changeType)) {
            executorService.schedule(() -> restoreStockFromLog(itemId, quantity), 1, TimeUnit.MINUTES);
        }
    }

    // 재고 복원 (타임아웃 기반)
    private void restoreStockFromLog(Long itemId, Integer quantity) {
        StockLog stockLog = stockLogRepository.findTopByItemIdAndChangeTypeOrderByCreatedAtDesc(itemId, "REDUCE")
                .orElseThrow(() -> new IllegalStateException("No stock log found for itemId: " + itemId));
        adjustStock(stockLog.getItemId(), stockLog.getQuantity(), "RESTORE");
        stockLogRepository.delete(stockLog);
    }

    // 주문 취소 (로그 기반)
    public void cancelStockLog(Long logId) {
        StockLog stockLog = stockLogRepository.findByLogIdAndChangeType(logId, "REDUCE")
                .orElseThrow(() -> new IllegalArgumentException("Invalid logId"));

        adjustStock(stockLog.getItemId(), stockLog.getQuantity(), "RESTORE");
        stockLogRepository.deleteById(logId);
    }
}