package shop.ninescent.mall.cartItem.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import shop.ninescent.mall.item.domain.Item;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonBackReference // Prevents serialization of cart reference
    private Cart cart; // Cart와 ManyToOne 관계

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item; // Item과 ManyToOne 관계

    @Column(nullable = false)
    private Boolean isSelected;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now(); // 생성 날짜 추가
}
