package shop.ninescent.mall.image.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.ninescent.mall.category.domain.Category;
import shop.ninescent.mall.image.dto.ImageRequestDTO;
import shop.ninescent.mall.image.util.FileNameUtil;
import shop.ninescent.mall.item.repository.CategoryRepository;

import java.io.IOException;

@Service
public class ProductImageService extends AbstractImageService{
    private final CategoryRepository categoryRepository;

    public ProductImageService(AmazonS3 amazonS3, CategoryRepository categoryRepository) {
        super(amazonS3);
        this.categoryRepository = categoryRepository;
    }

    /**
     * S3에 저장될 파일 경로를 생성하는 메서드
     * - 카테고리 ID를 기반으로 대분류 카테고리명을 조회하여 사용
     * - 메인 이미지는 "main.jpg"로 고정
     * - 상세 이미지는 자동으로 "detail1.jpg", "detail2.jpg"로 번호 부여
     */

    @Override
    protected String generateFilePath(ImageRequestDTO request) {
        String categoryName = getCategoryNameById(request.getCategoryId());

        String folderPath = String.format("products/%s/%s",
                FileNameUtil.sanitizeFileName(categoryName),
                FileNameUtil.sanitizeFileName(request.getItemName()));

        if ("main".equalsIgnoreCase(request.getImageType())) {
            return String.format("%s/main.jpg", folderPath);
        } else {
            return String.format("%s/%s", folderPath, getNextAvailableFileName(folderPath, "detail"));
        }
    }

    //카테고리ID -> 카테고리 대분류명으로 조회
    private String getCategoryNameById(Long categoryId){
        return categoryRepository.findById(categoryId)
                .map(Category::getCategoryName)
                .orElse("unknown");
    }


    @Override
    public String uploadImage(MultipartFile file, ImageRequestDTO request) throws IOException {
        String filePath = generateFilePath(request);
        return uploadToS3(file, filePath);
    }

    @Override
    public boolean deleteImage(ImageRequestDTO request) {
        String filePath = generateFilePath(request);
        return deleteFromS3(filePath);
    }

    @Override
    public String getImageUrl(ImageRequestDTO request) {
        String filePath = generateFilePath(request);
        return getImageUrlFromS3(filePath);
    }



}
