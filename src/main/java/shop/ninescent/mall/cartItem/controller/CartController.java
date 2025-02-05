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
    public ResponseEntity<?> addItemToCart(@PathVariable Long userNo, @RequestBody CartItemDTO cartItemDTO) {
        try {
            CartItemDTO addedItem = cartItemService.addItemToCart(userNo, cartItemDTO.getItemId(), cartItemDTO.getQuantity());
            return ResponseEntity.ok(addedItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
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
    public ResponseEntity<?> updateItem(@PathVariable Long userNo, @RequestBody CartItemDTO cartItemDTO) {
        try {
            CartItemDTO updatedItem = cartItemService.updateCartItem(userNo, cartItemDTO.getItemId(), cartItemDTO.getQuantity(), cartItemDTO.getAction());
            return ResponseEntity.ok(updatedItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }

    // 장바구니에서 'isSelected'가 true인 모든 아이템 삭제
    @DeleteMapping("/removeselected/{userNo}")
    public ResponseEntity<List<CartItemDTO>> removeSelectedItems(@PathVariable Long userNo) {
        try {
            // ✅ 선택된 아이템 삭제 후, 남아 있는 장바구니 상품 목록 반환
            List<CartItemDTO> remainingItems = cartItemService.removeSelectedItems(userNo);
            return ResponseEntity.ok(remainingItems);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    // 장바구니에서 특정 아이템(1개) 삭제 후 남은 장바구니 아이템 리스트 반환
    @DeleteMapping("/removeitem/{userNo}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long userNo, @RequestParam Long itemId) {
        try {
            List<CartItemDTO> remainingItems = cartItemService.removeItemFromCart(userNo, itemId);
            return ResponseEntity.ok(remainingItems);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }
}