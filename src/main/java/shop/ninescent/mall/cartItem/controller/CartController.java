package shop.ninescent.mall.cartItem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.cartItem.domain.CartItem;
import shop.ninescent.mall.cartItem.dto.CartDTO;
import shop.ninescent.mall.cartItem.dto.CartItemDTO;
import shop.ninescent.mall.cartItem.service.CartItemService;
import shop.ninescent.mall.cartItem.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartItemService cartItemService;
    private final CartService cartService;


    // 사용자의 장바구니에 담긴 물건 개수 조회
    @GetMapping("/count/{userNo}")
    public ResponseEntity<Integer> getCartCount(@PathVariable Long userNo){
        int cartCount = cartService.getCartCount(userNo);
        return ResponseEntity.ok(cartCount);
    }

    // 장바구니 조회
    @GetMapping("/detail/{userNo}")
    public ResponseEntity<CartDTO> getCartItems(@PathVariable Long userNo) {
        CartDTO cartDTO = cartService.getCartByUserNo(userNo);
        return ResponseEntity.ok(cartDTO);
    }

    // 장바구니에 상품 추가
    @PostMapping("/add/{userNo}")
    public ResponseEntity<String> addItemToCart(@PathVariable Long userNo, @RequestBody CartItemDTO cartItemDTO) {
        try {
            cartItemService.addItemToCart(userNo, cartItemDTO.getItemId(), cartItemDTO.getQuantity());
            return ResponseEntity.ok("Item added to the cart");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 장바구니 아이템 수량 변경
    // {
    //  "cartId": 1,
    //  "itemId": 2,
    //  "quantity": 1,
    //  "action": "decrease" // increase // set
    //}
    @PostMapping("/update/{userNo}")
    public ResponseEntity<String> updateItem(@PathVariable Long userNo, @RequestBody CartItemDTO cartItemDTO) {
        try {
            // 수량을 증가, 감소, 직접 지정하는 로직 처리
            cartItemService.updateCartItem(userNo, cartItemDTO.getItemId(), cartItemDTO.getQuantity(), cartItemDTO.getAction());
            return ResponseEntity.ok("Item updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 장바구니에서 'isSelected'가 true인 모든 아이템 삭제
    @DeleteMapping("/removeselected/{userNo}")
    public ResponseEntity<String> removeSelectedItems(@PathVariable Long userNo) {
        try {
            cartItemService.removeSelectedItems(userNo);
            return ResponseEntity.ok("Selected items removed from cart");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    // 장바구니에서 특정 아이템 삭제
    @DeleteMapping("/removeitem/{userNo}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long userNo,
                                                     @RequestParam Long itemId) {
        try {
            cartItemService.removeItemFromCart(userNo, itemId);
            return ResponseEntity.ok("Item removed from cart");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}