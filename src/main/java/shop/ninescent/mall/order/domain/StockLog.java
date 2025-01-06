package shop.ninescent.mall.order.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_log")
@Data
public class StockLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;
    @Column(nullable = false)
    private Long itemId;
    @Column(nullable = false)
    private String changeType; // "REDUCE", "RESTORE"
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public StockLog(Long itemId, Integer quantity, String changeType) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.changeType = changeType;
    }

    public StockLog() {

    }
}
