//package shop.ninescent.mall.order.domain;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import shop.ninescent.mall.item.domain.Item;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "stock_log")
//@Data
//public class StockLog {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long logId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "item_id", nullable = false)
//    private Item item;
//
//    @Column(nullable = false)
//    private String changeType; // "REDUCE", "RESTORE"
//    @Column(nullable = false)
//    private Integer quantity;
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    public StockLog(Item item, Integer quantity, String changeType) {
//        this.item = item;
//        this.quantity = quantity;
//        this.changeType = changeType;
//    }
//
//    public StockLog() {
//
//    }
//}
