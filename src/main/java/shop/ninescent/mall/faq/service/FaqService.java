package shop.ninescent.mall.faq.service;

import lombok.RequiredArgsConstructor;
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

        return FaqResponseDTO.builder()
                .faqId(savedFaq.getFaqId())
                .category(savedFaq.getCategory())
                .title(savedFaq.getTitle())
                .content(savedFaq.getContent())
                .regDate(LocalDateTime.now())
                .build();
    }

    public FaqResponseDTO updateFaq(Long faqId, UpdateFaqRequestDTO updateFaqRequestDTO) {
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new IllegalArgumentException("해당 faq가 존재하지 않습니다."));

        faq.setCategory(updateFaqRequestDTO.getCategory());
        faq.setTitle(updateFaqRequestDTO.getTitle());
        faq.setContent(updateFaqRequestDTO.getContent());

        Faq updatedFaq = faqRepository.save(faq);
        return FaqResponseDTO.builder()
                .faqId(updatedFaq.getFaqId())
                .category(updatedFaq.getCategory())
                .title(updatedFaq.getTitle())
                .content(updatedFaq.getContent())
                .regDate(updatedFaq.getRegDate())
                .build();
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

        return FaqResponseDTO.builder()
                .faqId(faq.getFaqId())
                .category(faq.getCategory())
                .title(faq.getTitle())
                .content(faq.getContent())
                .regDate(faq.getRegDate())
                .build();
    }

    public List<FaqResponseDTO> getFaqsByCategory(String category) {
        List<Faq> faqs;

        if ("all".equals(category)) {
            faqs = faqRepository.findAll();
        } else {
            faqs = faqRepository.findByCategory(category);
        }

        return faqs.stream()
                .map(faq -> {
                    FaqResponseDTO faqDTO = new FaqResponseDTO();
                    faqDTO.setFaqId(faq.getFaqId());
                    faqDTO.setCategory(faq.getCategory());
                    faqDTO.setTitle(faq.getTitle());
                    faqDTO.setContent(faq.getContent());
                    faqDTO.setRegDate(LocalDateTime.now());
                    return faqDTO;
                })
                .collect(Collectors.toList());
    }

    public void deleteFaq(Long faqId) {
        Faq faq = faqRepository.findById(faqId)
                .orElseThrow(() -> new RuntimeException("faq not found"));
        faqRepository.deleteById(faqId);
    }
}
