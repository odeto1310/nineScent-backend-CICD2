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
}
