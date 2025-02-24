package shop.ninescent.mall.cartItem.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import shop.ninescent.mall.member.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user; // User와 OneToOne 관계

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Marks this as the "parent" in the relationship --  JSON 직렬화 시 정상 직렬화
    @ToString.Exclude  // 순환 참조 방지
    private List<CartItem> cartItems = new ArrayList<>(); // CartItem과 OneToMany 관계

    @Column(nullable = false)
    private Integer totalCount = 0;
}