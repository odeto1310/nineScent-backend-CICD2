package shop.ninescent.mall.faq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.faq.dto.FaqRequestDTO;
import shop.ninescent.mall.faq.dto.FaqResponseDTO;
import shop.ninescent.mall.faq.dto.UpdateFaqRequestDTO;
import shop.ninescent.mall.faq.service.FaqService;

import java.util.List;

@RestController
@RequestMapping("/api/faq")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    @PostMapping
    public ResponseEntity<FaqResponseDTO> createFaq(@RequestBody FaqRequestDTO faqRequestDTO) {
        FaqResponseDTO faqResponseDTO = faqService.createFaq(faqRequestDTO);
        return ResponseEntity.ok(faqResponseDTO);
    }

    @PutMapping("/{faqId}")
    public ResponseEntity<FaqResponseDTO> updateFaq(@PathVariable Long faqId, @RequestBody UpdateFaqRequestDTO updateFaqRequestDTO) {
        FaqResponseDTO faqResponseDTO = faqService.updateFaq(faqId, updateFaqRequestDTO);
        return ResponseEntity.ok(faqResponseDTO);
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return faqService.getAllCategories();
    }

    @GetMapping("/{faqId}")
    public ResponseEntity<FaqResponseDTO> getFaq(@PathVariable Long faqId) {
        faqService.getFaq(faqId);
        return ResponseEntity.ok(faqService.getFaq(faqId));
    }

    @GetMapping("/category/{category}")
    public List<FaqResponseDTO> getFaqsByCategory(@PathVariable String category) {
        return faqService.getFaqsByCategory(category);
    }

    @GetMapping("/list/{category}")
    public Page<FaqResponseDTO> getFaqsByCategory(@PathVariable String category, @PageableDefault Pageable pageable) {
        return faqService.getFaqByPage(category, pageable);
    }

    @DeleteMapping("/{faqId}")
    public ResponseEntity<FaqResponseDTO> deleteFaq(@PathVariable Long faqId) {
        faqService.deleteFaq(faqId);
        return ResponseEntity.ok().build();
    }
}
