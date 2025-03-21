package shop.ninescent.mall.image.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.ninescent.mall.image.dto.ImageRequestDTO;

import java.io.IOException;

@Service
public class ReviewImageService extends AbstractImageService {
    protected ReviewImageService(AmazonS3 amazonS3) {
        super(amazonS3);
    }

    @Override
    protected String generateFilePath(ImageRequestDTO request) {
        String folderPath = String.format("review/%d", request.getReviewId());

        return String.format("%s/%s", folderPath, getNextAvailableFileName(folderPath, "reviewImage"));
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
