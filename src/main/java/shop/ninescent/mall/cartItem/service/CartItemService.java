package shop.ninescent.mall.cartItem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.cartItem.domain.Cart;
import shop.ninescent.mall.cartItem.domain.CartItem;
import shop.ninescent.mall.cartItem.dto.CartItemDTO;
import shop.ninescent.mall.cartItem.repository.CartItemRepository;
import shop.ninescent.mall.item.domain.Item;
import shop.ninescent.mall.item.repository.ItemRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final ItemRepository itemRepository;

//    // 장바구니 조회
//    public List<CartItem> getAllItems(Long userNo) {
//        Cart cart = cartService.getOrCreateCartByUserNo(userNo);
//        return cartItemRepository.findByCart(cart);
//    }

    // 장바구니에 상품 추가
    public CartItemDTO addItemToCart(Long userNo, Long itemId, Integer quantity) {
        Cart cart = cartService.getOrCreateCartByUserNo(userNo);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + itemId));

        CartItem existingCartItem = cartItemRepository.findByCartAndItem(cart, item);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartItemRepository.save(existingCartItem);
            return convertToCartItemDTO(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setItem(item);
            cartItem.setQuantity(quantity);
            cartItem.setIsSelected(true);
            cartItem.setCreatedDate(LocalDateTime.now());
            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);
            return convertToCartItemDTO(cartItem);
        }
    }

    // 장바구니 속 아이템 수량 업데이트
    public CartItemDTO updateCartItem(Long userNo, Long itemId, Integer quantity, String action) {
        Cart cart = cartService.getOrCreateCartByUserNo(userNo);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + itemId));

        CartItem cartItem = cartItemRepository.findByCartAndItem(cart, item);
        if (cartItem == null) {
            throw new IllegalArgumentException("Item not found in cart");
        }

        switch (action.toLowerCase()) {
            case "increase":
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                break;
            case "decrease":
                cartItem.setQuantity(Math.max(cartItem.getQuantity() - quantity, 0));
                break;
            case "set":
                cartItem.setQuantity(quantity);
                break;
            default:
                throw new IllegalArgumentException("Invalid action type");
        }

        cartItemRepository.save(cartItem);
        return convertToCartItemDTO(cartItem);
    }

    // 장바구니의 총 수량 업데이트
    private void updateCartTotalCount(Cart cart) {
        int totalCount = cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum();
        cart.setTotalCount(totalCount);
    }

    // 'isSelected'가 true인 모든 아이템 삭제
    public List<CartItemDTO> removeSelectedItems(Long userNo) {
        // 사용자의 장바구니 조회
        Cart cart = cartService.getOrCreateCartByUserNo(userNo);

        // 선택된 아이템 가져오기
        List<CartItem> selectedItems = cartItemRepository.findByCartAndIsSelectedTrue(cart);

        if (selectedItems.isEmpty()) {
            throw new IllegalArgumentException("No selected items to remove");
        }

        // 선택된 상품 삭제
        cartItemRepository.deleteAll(selectedItems);

        // 장바구니 총 수량 업데이트
        updateCartTotalCount(cart);

        // 삭제 후 남아 있는 장바구니 아이템 리스트 반환
        return cart.getCartItems().stream()
                .map(this::convertToCartItemDTO) // CartItem → CartItemDTO 변환
                .collect(Collectors.toList());
    }


    // 특정 아이템 삭제 후 남은 장바구니 아이템 리스트 반환
    public List<CartItemDTO> removeItemFromCart(Long userNo, Long itemId) {
        Cart cart = cartService.getOrCreateCartByUserNo(userNo);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + itemId));

        CartItem cartItem = cartItemRepository.findByCartAndItem(cart, item);
        if (cartItem == null) {
            throw new IllegalArgumentException("Item not found in cart");
        }

        cartItemRepository.delete(cartItem);

        // ✅ 삭제 후 장바구니 내 남아있는 아이템 리스트 반환
        return cart.getCartItems().stream()
                .map(this::convertToCartItemDTO)
                .collect(Collectors.toList());
    }


    private CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        Item item = cartItem.getItem();
        return new CartItemDTO(
                cartItem.getCartItemId(),
                item.getItemId(),
                item.getItemName(),
                cartItem.getQuantity(),
                cartItem.getIsSelected(),
                null, // action 값 (필요 시 설정)
                item.getItemSize(),
                BigDecimal.valueOf(item.getPrice()),
                item.getDiscountRate(),
                item.getDiscountedPrice() != null ? BigDecimal.valueOf(item.getDiscountedPrice()) : null,
                item.getStock(),
                item.getMainPhoto() // ✅ 대표 이미지 추가
        );
    }
}