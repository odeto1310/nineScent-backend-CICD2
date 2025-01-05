package shop.ninescent.mall.cartItem.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @Column(nullable = false)
    private Long userNo;
    @Column(nullable = false)
    private Integer totalCount;
}