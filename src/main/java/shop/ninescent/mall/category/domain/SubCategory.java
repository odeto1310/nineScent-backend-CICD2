package shop.ninescent.mall.category.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subCategoryId;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private String subCategoryName;
}
