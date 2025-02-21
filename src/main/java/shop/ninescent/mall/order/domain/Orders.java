package shop.ninescent.mall.order.domain;

import jakarta.persistence.*;
import lombok.Data;
import shop.ninescent.mall.address.domain.Address;
import shop.ninescent.mall.member.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user; // User와 연관 관계

    @ManyToOne
    @JoinColumn(name = "addr_no", nullable = false)
    private Address address; // Address와 연관 관계

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems = new ArrayList<>(); // OrderItems와 연관 관계

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(nullable = false)
    private boolean deliveryDone;
    @Column(nullable = false)
    private boolean paymentDone;
    @Column(nullable = false)
    private boolean refundChangeDone;

    @Column(nullable = false)
    private long totalPrice; // 원래 가격 총합

    @Column(nullable = false)
    private long totalDiscount; // 총 할인 금액

    @Column(nullable = false)
    private long finalAmount; // 총 결제 금액 (상품 금액 - 할인 + 배송비)

    @Column(nullable = false)
    private long shippingFee; // 배송비 정보 (10만 원 이상 무료)
}
