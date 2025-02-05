package shop.ninescent.mall.cartItem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.cartItem.domain.Cart;
import shop.ninescent.mall.cartItem.domain.CartItem;
import shop.ninescent.mall.cartItem.dto.CartDTO;
import shop.ninescent.mall.cartItem.dto.CartItemDTO;
import shop.ninescent.mall.cartItem.repository.CartRepository;
import shop.ninescent.mall.member.domain.User;
import shop.ninescent.mall.member.repository.UserRepository;
import shop.ninescent.mall.item.domain.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    // 사용자의 카트 조회 또는 생성
    public Cart getOrCreateCartByUserNo(Long userNo) {
        return cartRepository.findByUserUserNo(userNo).orElseGet(() -> {
            User user = userRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException("User not found: " + userNo));
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setTotalCount(0);
            return cartRepository.save(cart);
        });
    }

    // 사용자의 카트 정보 반환
    public CartDTO getCartByUserNo(Long userNo) {
        Cart cart = getOrCreateCartByUserNo(userNo);

        List<CartItemDTO> cartItems = cart.getCartItems().stream().map(cartItem -> {
            Item item = cartItem.getItem(); // 상품 정보 가져오기

            return new CartItemDTO(
                    cartItem.getCartItemId(),
                    item.getItemId(),
                    item.getItemName(),
                    cartItem.getQuantity(),
                    cartItem.getIsSelected(),
                    null, // action 값 (필요 시 설정)
                    item.getItemSize(),
                    BigDecimal.valueOf(item.getPrice()), // 가격
                    item.getDiscountRate(),
                    item.getDiscountedPrice() != null ? BigDecimal.valueOf(item.getDiscountedPrice()) : null,
                    item.getStock(),
                    item.getMainPhoto() // ✅ 대표 이미지 추가
            );
        }).collect(Collectors.toList());

        return new CartDTO(cart.getCartId(), cartItems);
    }

    // 사용자의 총 카트 수량 반환
    public int getCartCount(Long userNo) {
        Cart cart = getOrCreateCartByUserNo(userNo);
        return cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum();
    }
}