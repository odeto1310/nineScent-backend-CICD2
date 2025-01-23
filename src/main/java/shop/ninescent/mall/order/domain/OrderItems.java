package shop.ninescent.mall.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import shop.ninescent.mall.item.domain.Item;

@Entity
@Data
@Table(name = "order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Orders order; // Orders와 연관 관계

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item; // Item과 연관 관계

    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private long originalPrice;
    @Column(nullable = false)
    private long discountedPrice;
}