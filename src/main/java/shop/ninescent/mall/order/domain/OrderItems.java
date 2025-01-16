package shop.ninescent.mall.order.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;
    @Column(nullable = false)
    private int orderId;
    @Column(nullable = false)
    private long itemId;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private long originalPrice;
    @Column(nullable = false)
    private long discountedPrice;
}