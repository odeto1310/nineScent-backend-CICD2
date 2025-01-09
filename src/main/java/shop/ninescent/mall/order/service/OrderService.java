package shop.ninescent.mall.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.cartItem.domain.CartItem;
import shop.ninescent.mall.cartItem.repository.CartItemRepository;
import shop.ninescent.mall.address.domain.Address;
import shop.ninescent.mall.item.domain.Item;
import shop.ninescent.mall.item.repository.ItemRepository;
import shop.ninescent.mall.order.domain.StockLog;
import shop.ninescent.mall.order.dto.OrderItemDTO;
import shop.ninescent.mall.address.repository.AddressRepository;
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
    public OrderItemDTO prepareSingleOrder(Long itemId, Integer quantity, Long userNo, Long selectedAddrNo) {
        // stock 관리
        adjustStock(itemId, quantity, "REDUCE");

        // 기본 주소 또는 선택된 주소 가져오기
        Address address = (selectedAddrNo != null) ?
                addressRepository.findById(selectedAddrNo)
                                .orElseThrow(() -> new IllegalArgumentException("selected address not found"))
                : addressRepository.findByUserNoAndIsDefaultTrue(userNo)
                                .orElseThrow(() -> new IllegalArgumentException("Default address not found"));
        // 배송비 계산
        Long shippingFee = address.getIsExtraFee() ? 5000L : 0L;

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid itemId"));

        return new OrderItemDTO(
                item.getItemId(),
                item.getItemName(),
                quantity,
                item.getPrice(),
                item.getDiscountedPrice(),
                address,
                shippingFee
        );
    }

    // 장바구니 기반 주문
    public List<OrderItemDTO> prepareCartOrder(Long cartId, Long userNo, Long selectedAddrNo) {
        // 기본 주소 또는 선택된 주소 가져오기
        Address address = (selectedAddrNo != null) ?
                addressRepository.findById(selectedAddrNo)
                        .orElseThrow(() -> new IllegalArgumentException("selected address not found"))
                : addressRepository.findByUserNoAndIsDefaultTrue(userNo)
                .orElseThrow(() -> new IllegalArgumentException("Default address not found"));
        // 배송비 계산
        Long shippingFee = address.getIsExtraFee() ? 5000L : 0L;


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
                            address,
                            shippingFee
                    );
                })
                .collect(Collectors.toList());
    }

    // 배송비 조정

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