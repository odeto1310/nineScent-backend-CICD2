package shop.ninescent.mall.item.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.ninescent.mall.image.domain.ImageCategory;
import shop.ninescent.mall.image.dto.ImageRequestDTO;
import shop.ninescent.mall.image.service.ProductImageService;
import shop.ninescent.mall.item.domain.Item;
import shop.ninescent.mall.item.dto.ItemDTO;
import shop.ninescent.mall.item.dto.RecommendItem;
import shop.ninescent.mall.item.repository.ItemRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ProductImageService productImageService;
    private List<RecommendItem> recommendItemsList = new ArrayList<>();


    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // 특정 카테고리의 아이템 조회
    public List<Item> getItemsByCategory(Long categoryId) {
        return itemRepository.findByCategoryId(categoryId);
    }

    // 특정 하위 카테고리의 아이템 조회
    public List<Item> getItemsBySubCategory(Long subCategoryId) {
        return itemRepository.findBySubCategoryId(subCategoryId);
    }

    // 특정 카테고리 + 하위 카테고리 아이템 조회
    public List<Item> getItemsByCategoryAndSubCategory(Long categoryId, Long subCategoryId) {
        return itemRepository.findByCategoryIdAndSubCategoryId(categoryId, subCategoryId);
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
        //메인 이미지 업로드
        String mainImageUrl = null;
        if (mainImage != null && !mainImage.isEmpty()) {
            ImageRequestDTO request = new ImageRequestDTO(ImageCategory.PRODUCT, itemDTO.getCategoryId(), itemDTO.getItemName(), "main");
            mainImageUrl = productImageService.uploadImage(mainImage, request);
        }

        //상세 이미지 업로드
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

        //Item 엔티티 생성 및 저장
        Item item = itemDTO.toEntity();
        item.setMainPhoto(mainImageUrl);
        item.setDetailPhotos(detailImageUrls);
        return itemRepository.save(item);
    }

    /**
     *상품 삭제 (이미지도 함께 삭제)
     */
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 이미지 삭제
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

        // 상품 삭제
        itemRepository.delete(item);
    }

    // 서버 실행 시 상품추천
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        updateRecommendItems();
    }

    // 매달 1일 추천목록 업데이트
    @Scheduled(cron = "0 0 0 1 * ?")
    public void updateRecommendItems() {
        List<Item> recommendItems = itemRepository.recommendItems();

        recommendItemsList = recommendItems.stream().map(item -> {
            RecommendItem recommendItem = new RecommendItem();
            recommendItem.setItemId(item.getItemId());
            recommendItem.setItemName(item.getItemName());
            recommendItem.setItemSize(item.getItemSize());
            recommendItem.setItemTitle(item.getItemTitle());
            recommendItem.setPrice(item.getPrice());
            recommendItem.setDiscountRate(item.getDiscountRate());
            recommendItem.setDiscountedPrice(item.getDiscountedPrice());
            recommendItem.setMainPhoto(item.getMainPhoto());
            return recommendItem;
        }).toList();
    }

    public List<RecommendItem> recommendItemsList() {
        return recommendItemsList;
    }
}