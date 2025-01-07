package shop.ninescent.mall.item.entity;

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
    private int itemId;

    private int subCategoryId;
    private String itemName;
    private int categoryId;
    private String itemSize;
    private String itemDescription;
    private int price;
    private int discountRate;
    private int discountedPrice;
    private LocalDate discountStart;
    private LocalDate discountEnd;
    private String discountDescription;
    private int stock;
    private String mainPhoto;
    private String detailPhoto;
}