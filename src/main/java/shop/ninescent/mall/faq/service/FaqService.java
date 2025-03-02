package shop.ninescent.mall.faq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.faq.domain.Faq;
import shop.ninescent.mall.faq.dto.FaqRequestDTO;
import shop.ninescent.mall.faq.dto.FaqResponseDTO;
import shop.ninescent.mall.faq.dto.UpdateFaqRequestDTO;
import shop.ninescent.mall.faq.repository.FaqRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;

    public FaqResponseDTO createFaq(FaqRequestDTO faqRequestDTO) {
        Faq faq = Faq.builder()
                .category(faqRequestDTO.getCategory())
                .title(faqRequestDTO.getTitle())
                .content(faqRequestDTO.getContent())
                .regDate(LocalDateTime.now())
                .build();

        Faq savedFaq = faqRepository.save(faq);
        return toResponseDTO(savedFaq);
    }

    public FaqResponseDTO updateFaq(Long faqId, UpdateFaqRequestDTO updateFaqRequestDTO) {
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalArgumentException("해당 FAQ가 존재하지 않습니다."));

        faq.setCategory(updateFaqRequestDTO.getCategory());
        faq.setTitle(updateFaqRequestDTO.getTitle());
        faq.setContent(updateFaqRequestDTO.getContent());

        Faq updatedFaq = faqRepository.save(faq);
        return toResponseDTO(updatedFaq);
    }

    public List<String> getAllCategories() {
        List<String> categories = faqRepository.findDistinctCategories();

        if (!categories.contains("all") || categories.isEmpty()) {
            categories.add(0, "all");
        }

        return categories;
    }

    public FaqResponseDTO getFaq(Long faqId) {
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalArgumentException("해당 FAQ가 존재하지 않습니다."));

        return toResponseDTO(faq);
    }

    public List<FaqResponseDTO> getFaqsByCategory(String category) {
        List<Faq> faqs;

        if ("all".equals(category)) {
            faqs = faqRepository.findAll();
        } else {
            faqs = faqRepository.findByCategory(category);
        }

        return faqs.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<FaqResponseDTO> getFaqByPage(String category, Pageable pageable) {
        Page<Faq> faqs;

        if ("all".equals(category)) {
            faqs = faqRepository.findAll(pageable);
        } else {
            faqs = faqRepository.findByCategory(category, pageable);
        }

        return faqs.map(this::toResponseDTO);
    }

    public void deleteFaq(Long faqId) {
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new RuntimeException("해당 FAQ가 존재하지 않습니다"));

        faqRepository.deleteById(faqId);
    }

    private FaqResponseDTO toResponseDTO(Faq faq) {
        return FaqResponseDTO.builder()
                .faqId(faq.getFaqId())
                .category(faq.getCategory())
                .title(faq.getTitle())
                .content(faq.getContent())
                .regDate(faq.getRegDate())
                .build();
    }
}
