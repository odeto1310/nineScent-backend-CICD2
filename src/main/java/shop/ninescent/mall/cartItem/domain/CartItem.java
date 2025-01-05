package shop.ninescent.mall.cartItem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
    @Column(nullable = false)
    private Long cartId;
    @Column(nullable = false)
    private Long itemId;
    @Column(nullable = false)
    private Boolean isSelected;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now(); // 생성 날짜 추가
}
