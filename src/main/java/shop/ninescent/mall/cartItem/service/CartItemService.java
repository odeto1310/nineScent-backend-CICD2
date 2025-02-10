package shop.ninescent.mall.cartItem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.cartItem.domain.Cart;
import shop.ninescent.mall.cartItem.domain.CartItem;
import shop.ninescent.mall.cartItem.repository.CartItemRepository;
import shop.ninescent.mall.item.domain.Item;
import shop.ninescent.mall.item.repository.ItemRepository;

import java.time.LocalDateTime;
import java.util.List;

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
    public void addItemToCart(Long userNo, Long itemId, Integer quantity) {
        Cart cart = cartService.getOrCreateCartByUserNo(userNo);

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Item not found: " + itemId));

        CartItem existingCartItem = cartItemRepository.findByCartAndItem(cart, item);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setItem(item);
            cartItem.setQuantity(quantity);
            cartItem.setIsSelected(true);
            cartItem.setCreatedDate(LocalDateTime.now());
            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);
        }

        updateCartTotalCount(cart);
    }

    // 장바구니 속 아이템 수량 업데이트
    public void updateCartItem(Long userNo, Long itemId, Integer quantity, String action) {
        Cart cart = cartService.getOrCreateCartByUserNo(userNo);

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Item not found: " + itemId));

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
        updateCartTotalCount(cart);
    }

    // 장바구니의 총 수량 업데이트
    private void updateCartTotalCount(Cart cart) {
        int totalCount = cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum();
        cart.setTotalCount(totalCount);
    }

    // 'isSelected'가 true인 모든 아이템 삭제
    public void removeSelectedItems(Long userNo) {
        Cart cart = cartService.getOrCreateCartByUserNo(userNo);

        List<CartItem> selectedItems = cartItemRepository.findByCartAndIsSelectedTrue(cart);
        if (selectedItems.isEmpty()) {
            throw new IllegalArgumentException("No selected items to remove");
        }

        cartItemRepository.deleteAll(selectedItems);
        updateCartTotalCount(cart);
    }

    // 특정 아이템 삭제
    public void removeItemFromCart(Long userNo, Long itemId) {
        Cart cart = cartService.getOrCreateCartByUserNo(userNo);

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Item not found: " + itemId));

        CartItem cartItem = cartItemRepository.findByCartAndItem(cart, item);
        if (cartItem == null) {
            throw new IllegalArgumentException("Item not found in cart");
        }

        cartItemRepository.delete(cartItem);
        updateCartTotalCount(cart);
    }
}