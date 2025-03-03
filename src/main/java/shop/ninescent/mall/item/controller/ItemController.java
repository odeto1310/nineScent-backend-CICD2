package shop.ninescent.mall.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.ninescent.mall.item.domain.Item;
import shop.ninescent.mall.item.dto.ItemDTO;
import shop.ninescent.mall.item.service.ItemService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**
     * ✅ 상품 등록 (이미지 포함)
     */
    @PostMapping("/create")
    public ResponseEntity<Item> createItem(
            @ModelAttribute ItemDTO itemDTO,
            @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
            @RequestParam(value = "detailImages", required = false) List<MultipartFile> detailImages) throws IOException {

        Item savedItem = itemService.createItem(itemDTO, mainImage, detailImages);
        return ResponseEntity.ok(savedItem);
    }

    /**
     * ✅ 모든 상품 조회
     */
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    /**
     * ✅ 특정 상품 조회
     */
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    /**
     * ✅ 상품 수정
     */
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable("id") long id, @RequestBody Item item) {
        item.setItemId(id);
        return itemService.saveItem(item);
    }

    /**
     * ✅ 상품 삭제
     */
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItem(id);
    }
}
