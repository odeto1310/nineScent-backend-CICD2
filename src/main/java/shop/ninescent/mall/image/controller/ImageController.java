package shop.ninescent.mall.image.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.ninescent.mall.image.dto.ImageRequestDTO;
import shop.ninescent.mall.image.service.ProductImageService;
import shop.ninescent.mall.image.service.ReviewImageService;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ProductImageService productImageService;
    private final ReviewImageService reviewImageService;


    //상품 이미지 등록, 삭제, 조회
    @PostMapping("/products/upload")
    public ResponseEntity<String> uploadProductImage(@RequestParam MultipartFile file, @RequestBody ImageRequestDTO request) throws IOException{
        return ResponseEntity.ok(productImageService.uploadImage(file, request));
    }

    @DeleteMapping("/products/delete")
    public ResponseEntity<Boolean> deleteProductImage(@RequestBody ImageRequestDTO request) {
        return ResponseEntity.ok(productImageService.deleteImage(request));
    }

    @GetMapping("/products/url")
    public ResponseEntity<String> getProductImageUrl(@RequestBody ImageRequestDTO request) {
        return ResponseEntity.ok(productImageService.getImageUrl(request));
    }


    //리뷰 이미지 등록, 삭제, 조회
    @PostMapping("/reviews/upload")
    public ResponseEntity<String> uploadReviewImage(
            @RequestParam MultipartFile file,
            @RequestBody ImageRequestDTO request) throws IOException {
        return ResponseEntity.ok(reviewImageService.uploadImage(file, request));
    }

    @DeleteMapping("/reviews/delete")
    public ResponseEntity<Boolean> deleteReviewImage(@RequestBody ImageRequestDTO request) {
        return ResponseEntity.ok(reviewImageService.deleteImage(request));
    }


    @GetMapping("/reviews/url")
    public ResponseEntity<String> getReviewImageUrl(@RequestBody ImageRequestDTO request) {
        return ResponseEntity.ok(reviewImageService.getImageUrl(request));
    }


}
