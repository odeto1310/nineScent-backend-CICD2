package shop.ninescent.mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.ninescent.mall.item.domain.Item;
import shop.ninescent.mall.item.service.ItemService;
import shop.ninescent.mall.item.service.S3Service;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    private S3Service s3Service;

    @GetMapping("/get-img")
    public ResponseEntity<String> getImgUrl(@RequestParam("item") String item) {
        return ResponseEntity.ok(s3Service.makeImgUrl(item));
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("images") MultipartFile[] files) {
        if (files.length > 15) {
            return ResponseEntity.badRequest().body("Error: Cannot upload more than 15 files at a time.");
        }
        for (MultipartFile file : files) {
            try {
                s3Service.uploadFile(file);
            } catch (IOException e) {
                return ResponseEntity.internalServerError().body("File upload failed: " + e.getMessage());
            }
        }
        return ResponseEntity.ok("Files uploaded successfully");
    }

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable("id") long id, @RequestBody Item item) {
        item.setItemId(id);
        return itemService.saveItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItem(id);
    }
}