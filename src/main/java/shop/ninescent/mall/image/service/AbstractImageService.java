package shop.ninescent.mall.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import shop.ninescent.mall.image.dto.ImageRequestDTO;
import shop.ninescent.mall.image.util.FileNameUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class AbstractImageService implements ImageServiceInterface{
    protected final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    protected String bucketName;

    @Value("${cloud.aws.s3.bucketUrl}")
    private String bucketUrl;

    protected AbstractImageService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    protected String uploadToS3(MultipartFile file, String filePath) throws IOException {
        File convertedFile = convertMultipartFileToFile(file);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        PutObjectRequest putRequest = new PutObjectRequest(bucketName, filePath, convertedFile)
                .withMetadata(metadata);


        amazonS3.putObject(putRequest);
        convertedFile.delete();
        return bucketUrl + "/" + filePath;
    }

    protected boolean deleteFromS3(String filePath) {
        if (amazonS3.doesObjectExist(bucketName, filePath)) {
            amazonS3.deleteObject(bucketName, filePath);
            return true;
        }
        return false;
    }

    protected String getImageUrlFromS3(String filePath) {
        String imageUrl = bucketUrl + "/" + filePath;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            return (connection.getResponseCode() == 200) ? imageUrl : null;
        } catch (Exception e) {
            return null;
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(FileNameUtil.sanitizeFileName(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }

    protected String getNextAvailableFileName(String folderPath, String baseName) {
        int index = 1;
        while (amazonS3.doesObjectExist(bucketName, String.format("%s/%s%d.jpg", folderPath, baseName, index))) {
            index++;
        }
        return String.format("%s%d.jpg", baseName, index);
    }




    protected abstract String generateFilePath(ImageRequestDTO request);

}
