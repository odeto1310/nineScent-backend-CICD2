package shop.ninescent.mall.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.ninescent.mall.category.dto.CategoryResponseDTO;
import shop.ninescent.mall.category.dto.ItemListResponseDTO;
import shop.ninescent.mall.category.dto.SubCategoryResponseDTO;
import shop.ninescent.mall.category.service.GetCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final GetCategoryService getCategoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        return ResponseEntity.ok(getCategoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<List<SubCategoryResponseDTO>> getSubCategories(@PathVariable Long categoryId) {
        return ResponseEntity.ok(getCategoryService.getSubCategories(categoryId));
    }

    @GetMapping("/{subCategoryId}/items")
    public ResponseEntity<List<ItemListResponseDTO>> getItemList(@PathVariable Long subCategoryId) {
        return ResponseEntity.ok(getCategoryService.getItemLists(subCategoryId));
    }
}
