package shop.ninescent.mall.category.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private Long subCategoryId;

    @Column(nullable = false)
    private String size;

    private String text;

    @Column(nullable = false)
    private Integer price;

    private Integer discountRate;

    @Column(nullable = false)
    private Integer discountedPrice;

    private Date discountStart;

    private Date discountEnd;

    private String discountDesc;

    @Column(nullable = false)
    private String stock;

    private String photo;

    private String detail;
}
