package shop.ninescent.mall.item.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    @Column(nullable = false)
    private Long subCategoryId;
    @Column(nullable = false)
    private String itemName;
    @Column(nullable = false)
    private Integer categoryId;
    @Column(nullable = true)
    private String itemSize;
    @Column(nullable = true)
    private String itemDescription;
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
    private String discountDescription;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = true)
    private String mainPhoto;
    @Column(nullable = true)
    private String detailPhoto;
}