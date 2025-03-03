package shop.ninescent.mall.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.ninescent.mall.image.domain.ImageCategory;
import shop.ninescent.mall.image.dto.ImageRequestDTO;
import shop.ninescent.mall.image.service.ProductImageService;
import shop.ninescent.mall.item.domain.Item;
import shop.ninescent.mall.item.dto.ItemDTO;
import shop.ninescent.mall.item.repository.ItemRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ProductImageService productImageService;


    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

//    public void deleteItem(Long id) {
//        itemRepository.deleteById(id);
//    }

    public Item createItem(ItemDTO itemDTO, MultipartFile mainImage, List<MultipartFile> detailImages) throws IOException {
        // 📌 메인 이미지 업로드
        String mainImageUrl = null;
        if (mainImage != null && !mainImage.isEmpty()) {
            ImageRequestDTO request = new ImageRequestDTO(ImageCategory.PRODUCT, itemDTO.getCategoryId(), itemDTO.getItemName(), "main");
            mainImageUrl = productImageService.uploadImage(mainImage, request);
        }

        // 📌 상세 이미지 업로드
        List<String> detailImageUrls = null;
        if (detailImages != null && !detailImages.isEmpty()) {
            detailImageUrls = detailImages.stream()
                    .map(file -> {
                        try {
                            ImageRequestDTO request = new ImageRequestDTO(ImageCategory.PRODUCT, itemDTO.getCategoryId(), itemDTO.getItemName(), "detail");
                            return productImageService.uploadImage(file, request);
                        } catch (IOException e) {
                            throw new RuntimeException("이미지 업로드 실패", e);
                        }
                    })
                    .collect(Collectors.toList());
        }

        // 📌 Item 엔티티 생성 및 저장
        Item item = itemDTO.toEntity();
        item.setMainPhoto(mainImageUrl);
        item.setDetailPhotos(detailImageUrls);
        return itemRepository.save(item);
    }

    /**
     * ✅ 상품 삭제 (이미지도 함께 삭제)
     */
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 📌 이미지 삭제
        if (item.getMainPhoto() != null) {
            ImageRequestDTO request = new ImageRequestDTO(ImageCategory.PRODUCT, item.getCategoryId(), item.getItemName(), "main");
            productImageService.deleteImage(request);
        }

        if (item.getDetailPhotos() != null) {
            for (String imageUrl : item.getDetailPhotos()) {
                ImageRequestDTO request = new ImageRequestDTO(ImageCategory.PRODUCT, item.getCategoryId(), item.getItemName(), "detail");
                productImageService.deleteImage(request);
            }
        }

        // 📌 상품 삭제
        itemRepository.delete(item);
    }
}