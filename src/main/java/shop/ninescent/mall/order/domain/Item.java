package shop.ninescent.mall.order.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "item")
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    @Column(nullable = false)
    private String itemName;
    @Column(nullable = false)
    private Long subCategoryId;
    @Column(nullable = false)
    private Integer categoryId;
    @Column(nullable = true)
    private String size;
    @Column(nullable = true)
    private String description;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = true)
    private Integer discountRate; // 할인율
    @Column(nullable = true)
    private Long discountedPrice;
    @Column(nullable = true)
    private LocalDate discountStart;
    @Column(nullable = true)
    private LocalDate discountEnd;
    @Column(nullable = true)
    private String discountDesc;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = true)
    private String photo;
    @Column(nullable = true)
    private String detail;
}
