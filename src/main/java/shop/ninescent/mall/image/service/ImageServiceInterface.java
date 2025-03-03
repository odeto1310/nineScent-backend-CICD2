package shop.ninescent.mall.image.service;

import org.springframework.web.multipart.MultipartFile;
import shop.ninescent.mall.image.dto.ImageRequestDTO;

import java.io.IOException;

public interface ImageServiceInterface {
    String uploadImage(MultipartFile file, ImageRequestDTO request) throws IOException;
    boolean deleteImage(ImageRequestDTO request);
    String getImageUrl(ImageRequestDTO request);
}
