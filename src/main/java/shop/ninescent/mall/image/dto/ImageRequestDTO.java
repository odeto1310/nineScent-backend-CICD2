package shop.ninescent.mall.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.ninescent.mall.image.domain.ImageCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequestDTO {
    private ImageCategory imageCategory;
    private Long categoryId;
    private String itemName;
    private Long reviewId;
    private String section;
    private String imageType; //main, detail, banner1, reviewImage

    public ImageRequestDTO(ImageCategory imageCategory, Long categoryId, String itemName, String imageType) {
        this.imageCategory = imageCategory;
        this.categoryId = categoryId;
        this.itemName = itemName;
        this.imageType = imageType;
    }
}
