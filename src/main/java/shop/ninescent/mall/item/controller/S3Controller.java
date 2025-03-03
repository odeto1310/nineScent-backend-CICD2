package shop.ninescent.mall.item.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.ninescent.mall.item.domain.Item;
import shop.ninescent.mall.item.dto.ItemDTO;
import shop.ninescent.mall.item.service.ImageService;
import shop.ninescent.mall.item.service.ItemService;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class S3Controller {
    private final ImageService imageService;
    private final ItemService itemService;

    public S3Controller(ImageService imageService, ItemService itemService) {
        this.imageService = imageService;
        this.itemService = itemService;
    }

  //이미지만 업로드
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("itemName") String itemName,
            @RequestParam("imageType") String imageType) {
        try {
            String imageUrl = imageService.uploadImage(file, categoryId, itemName, imageType);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Image upload failed: " + e.getMessage());
        }
    }

  //아이템 전체 등록
  @PostMapping("/upload-and-create-item")
  public ResponseEntity<Item> uploadImageAndCreateItem(
          @ModelAttribute ItemDTO itemDTO,
          @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
          @RequestParam(value = "detailImage", required = false) MultipartFile detailImage) {

      try {
          // 📌 이미지 업로드 후 URL 저장
          if (mainImage != null && !mainImage.isEmpty()) {
              itemDTO.setPhoto(imageService.uploadImage(mainImage, itemDTO.getCategoryId(), itemDTO.getItemName(), "main"));
          }

          if (detailImage != null && !detailImage.isEmpty()) {
              itemDTO.setDetail(imageService.uploadImage(detailImage, itemDTO.getCategoryId(), itemDTO.getItemName(), "detail"));
          }

          // 📌 DTO를 엔티티로 변환하여 저장
          Item savedItem = itemService.saveItem(itemDTO.toEntity());
          return ResponseEntity.ok(savedItem);

      } catch (IOException e) {
          return ResponseEntity.status(500).body(null);
      }
  }


    @GetMapping("/mainPhotoUrl")
    public ResponseEntity<String> getMainImageUrl(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("itemName") String itemName) {
        String imageUrl = imageService.getMainImageUrl(categoryId, itemName);
        return imageUrl != null ? ResponseEntity.ok(imageUrl) : ResponseEntity.notFound().build();
    }


    @GetMapping("/detailPhotoUrl")
    public ResponseEntity<String> getDetailImageUrl(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("itemName") String itemName,
            @RequestParam("index") int index) {
        String imageUrl = imageService.getDetailImageUrl(categoryId, itemName, index);
        return imageUrl != null ? ResponseEntity.ok(imageUrl) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImage(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("itemName") String itemName,
            @RequestParam("imageType") String imageType) {
        return imageService.handleDeleteImage(categoryId, itemName, imageType);
    }


}
