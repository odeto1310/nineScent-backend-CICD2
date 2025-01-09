package shop.ninescent.mall.order.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    @Column(nullable = false)
    private String userNo;
    @Column(nullable = false)
    private long addrNo;
    @Column(nullable = false)
    private String orderDate;
    @Column(nullable = false)
    private boolean deliveryDone;
    @Column(nullable = false)
    private boolean paymentDone;
    @Column(nullable = false)
    private boolean refundChangeDone;
}
