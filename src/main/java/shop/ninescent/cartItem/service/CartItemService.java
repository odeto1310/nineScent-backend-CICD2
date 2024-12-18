package shop.ninescent.cartItem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.ninescent.cartItem.domain.Cart;
import shop.ninescent.cartItem.domain.CartItem;
import shop.ninescent.cartItem.repository.CartItemRepository;
import shop.ninescent.cartItem.repository.CartRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;

    // 장바구니 조회
    public List<CartItem> getAllItems(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    // 장바구니 상품 추가
    public void addItemToCart(Long cartId, Long itemId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        // cartId와 itemId로 기존 아이템이 있는지 확인
        CartItem existingCartItem = cartItemRepository.findByCartIdAndItemId(cartId, itemId);

        if (existingCartItem != null) {
            // 기존 아이템이 있으면 수량 증가
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            existingCartItem.setCreatedDate(LocalDateTime.now()); // createdDate는 수정된 시간으로 갱신
            cartItemRepository.save(existingCartItem);
        } else {

            // 장바구니에 아이템 추가

            CartItem cartItem = new CartItem();
            cartItem.setCartId(cartId);
            cartItem.setItemId(itemId);
            cartItem.setQuantity(quantity);
            cartItem.setIsSelected(true);
            cartItem.setCreatedDate(LocalDateTime.now());
            // 장바구니 아이템 저장
            cartItemRepository.save(cartItem);
        }
        // 장바구니의 total_count 갱신
        updateCartTotalCount(cartId);
    }

    // 장바구니 자체의 quantity 보여주는거
    private void updateCartTotalCount(Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        int totalCount = cartItems.stream().mapToInt(CartItem::getQuantity).sum();
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        cart.setTotalCount(totalCount);
        cartRepository.save(cart);
    }

    // 장바구니 속 아이템 수량 update
    public void updateCartItem(Long cartId, Long itemId, Integer quantity, String action) {
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cartId, itemId);

        if (cartItem == null) {
            throw new IllegalArgumentException("Item not found in cart");
        }

        switch (action.toLowerCase()) {
            case "increase":
                cartItem.setQuantity(cartItem.getQuantity() + quantity);  // 수량 증가
                break;
            case "decrease":
                if (cartItem.getQuantity() > quantity) {
                    cartItem.setQuantity(cartItem.getQuantity() - quantity);  // 수량 감소
                } else {
                    cartItem.setQuantity(0);  // 수량이 1인 경우 0으로 설정 (또는 삭제 처리 가능)
                }
                break;
            case "set":
                cartItem.setQuantity(quantity);  // 직접 지정된 수량으로 설정
                break;
            default:
                throw new IllegalArgumentException("Invalid action type");
        }
        cartItem.setCreatedDate(LocalDateTime.now()); // 변경 시간 갱신
        cartItemRepository.save(cartItem);
        // 장바구니의 total_count 갱신
        updateCartTotalCount(cartId);
    }

    // 'isSelected'가 true인 모든 아이템 삭제
    public void removeSelectedItems(Long cartId) {
        List<CartItem> selectedItems = cartItemRepository.findByCartIdAndIsSelectedTrue(cartId);
        if (selectedItems.isEmpty()) {
            throw new IllegalArgumentException("No selected items to remove");
        }

        cartItemRepository.deleteAll(selectedItems);
        // 장바구니의 total_count 갱신
        updateCartTotalCount(cartId);
    }

    // 장바구니에서 특정 아이템 삭제 (cartId와 itemId 기준)
    public void removeItemFromCart(Long cartId, Long itemId) {
        // cartId와 itemId로 해당 아이템 조회
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cartId, itemId);

        if (cartItem == null) {
            throw new IllegalArgumentException("Item not found in cart");
        }
        cartItemRepository.delete(cartItem);
        // 장바구니의 total_count 갱신
        updateCartTotalCount(cartId);
    }

}

