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
        List<CartItemDTO> cartItems = cart.getCartItems().stream()
                .map(cartItem -> new CartItemDTO(
                        cartItem.getCartItemId(),
                        cartItem.getItem().getItemId(),
                        cartItem.getItem().getItemName(),
                        cartItem.getQuantity(),
                        cartItem.getItem().getStock(),
                        cartItem.getIsSelected(),
                        null, // Action 필요 없음
                        cartItem.getItem().getPrice(), // 가격 추가
                        cartItem.getItem().getItemDescription(), // 설명 추가
                        cartItem.getItem().getMainPhoto(), // 대표 이미지 추가
                        cartItem.getItem().getItemSize()
                ))
                .collect(Collectors.toList());

        return new CartDTO(cart.getCartId(), cartItems);
    }

    // 사용자의 총 카트 수량 반환
    public int getCartCount(Long userNo) {
        Cart cart = getOrCreateCartByUserNo(userNo);
        return cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum();
    }
}