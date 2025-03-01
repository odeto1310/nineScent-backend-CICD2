package shop.ninescent.mall.item.service;


import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import shop.ninescent.mall.item.repository.CategoryRepository;
import shop.ninescent.mall.category.domain.Category;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ImageService {
    private final AmazonS3 amazonS3;
    private final CategoryRepository categoryRepository;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;
    @Value("${cloud.aws.s3.bucketUrl}")
    private String bucketUrl;

    public ImageService(AmazonS3 amazonS3, CategoryRepository categoryRepository) {
        this.amazonS3 = amazonS3;
        this.categoryRepository = categoryRepository;
    }

    //카테고리 Id에서 이름 가져오는 메서드
    private String getCategoryNameById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(Category::getCategoryName)
                .orElse("unknown"); // 만약 해당 ID가 없다면 "unknown" 반환
    }

    //메인 사진 url 가져오기
    public String getMainImageUrl(Long categoryId, String itemName) {
        String categoryName = getCategoryNameById(categoryId);
        String imageUrl = String.format("%s/%s/%s/main.jpg", bucketUrl, categoryName, itemName);
        return doesImageExist(imageUrl) ? imageUrl : null;
    }

    //상세사진 url 가져오기
    public String getDetailImageUrl(Long categoryId, String itemName, int index) {
        String categoryName = getCategoryNameById(categoryId);
        String imageUrl = String.format("%s/%s/%s/detail%d.jpg", bucketUrl, categoryName, itemName, index);
        return doesImageExist(imageUrl) ? imageUrl : null;
    }

    //이미지 업로드
    public String uploadImage(MultipartFile file, Long categoryId, String itemName, String imageType) throws IOException {
        String categoryName = getCategoryNameById(categoryId);
        File convertedFile = convertMultipartFileToFile(file);
        String fileName = String.format("%s/%s/%s.jpg", categoryName, itemName, imageType);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        PutObjectRequest putRequest = new PutObjectRequest(bucketName, fileName, convertedFile)
                .withMetadata(metadata);

        amazonS3.putObject(putRequest);
        convertedFile.delete();

        return bucketUrl + "/" + fileName;
    }



    //이미지 존재 여부 확인
    private boolean doesImageExist(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (responseCode == 200);
        } catch (Exception e) {
            return false; // 오류 발생 시 이미지 없음으로 간주
        }
    }

    //멀티파트 파일 변환
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fileOutputStream = new FileOutputStream(convertedFile)) {
            fileOutputStream.write(file.getBytes());
        }
        return convertedFile;
    }


    //S3에서 이미지 삭제를 위한 메서드
    public ResponseEntity<String> handleDeleteImage(Long categoryId, String itemName, String imageType) {
        try {
            boolean deleted = deleteImage(categoryId, itemName, imageType);
            if (deleted) {
                return ResponseEntity.ok("이미지가 삭제되었습니다.");
            } else {
                return ResponseEntity.status(404).body("이미지를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("이미지 삭제 실패: " + e.getMessage());
        }
    }

    private boolean deleteImage(Long categoryId, String itemName, String imageType) {
        String categoryName = getCategoryNameById(categoryId);
        String fileName = String.format("%s/%s/%s.jpg", categoryName, itemName, imageType);

        if (amazonS3.doesObjectExist(bucketName, fileName)) {
            amazonS3.deleteObject(bucketName, fileName);
            return true;
        }
        return false;
    }



}
