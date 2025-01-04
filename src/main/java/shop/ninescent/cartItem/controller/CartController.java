package shop.ninescent.cartItem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.cartItem.domain.Cart;
import shop.ninescent.cartItem.domain.CartItem;
import shop.ninescent.cartItem.domain.CartItemDTO;
import shop.ninescent.cartItem.service.CartItemService;
import shop.ninescent.cartItem.service.CartService;

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
    @GetMapping("/detail/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long cartId) {
        List<CartItem> cartItems = cartItemService.getAllItems(cartId);
        return ResponseEntity.ok(cartItems);
    }
    // 장바구니에 상품 추가
    // test {
    //  "cartId": 1,
    //  "itemId": 2,
    //  "quantity": 1
    //}
    @PostMapping("/add")
    public ResponseEntity<String> addItemToCart(@RequestBody CartItemDTO cartItemDTO) {
        try {
            cartItemService.addItemToCart(cartItemDTO.getCartId(), cartItemDTO.getItemId(), cartItemDTO.getQuantity());
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
    @PostMapping("/update")
    public ResponseEntity<String> updateItem(@RequestBody CartItemDTO cartItemDTO) {
        try {
            // 수량을 증가, 감소, 직접 지정하는 로직 처리
            cartItemService.updateCartItem(cartItemDTO.getCartId(), cartItemDTO.getItemId(), cartItemDTO.getQuantity(), cartItemDTO.getAction());
            return ResponseEntity.ok("Item updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 장바구니에서 'isSelected'가 true인 모든 아이템 삭제
    @DeleteMapping("/removeselected")
    public ResponseEntity<String> removeSelectedItems(@RequestParam Long cartId) {
        try {
            cartItemService.removeSelectedItems(cartId);
            return ResponseEntity.ok("Selected items removed from cart");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    // 장바구니에서 특정 아이템 삭제
    @DeleteMapping("/removeitem")
    public ResponseEntity<String> removeItemFromCart(@RequestParam Long cartId,
                                                     @RequestParam Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok("Item removed from cart");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}