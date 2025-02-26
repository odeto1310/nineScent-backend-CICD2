package shop.ninescent.mall.qna.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.qna.dto.QnaRequestDTO;
import shop.ninescent.mall.qna.dto.QnaResponseDTO;
import shop.ninescent.mall.qna.dto.UpdateQnaRequestDTO;
import shop.ninescent.mall.qna.service.QnaService;

import java.util.List;

@RestController
@RequestMapping("/api/qna")
@RequiredArgsConstructor
public class QnaBoardController {

    private final QnaService qnaService;

    @PostMapping
    public ResponseEntity<QnaResponseDTO> createQna(@RequestBody QnaRequestDTO qnaRequestDTO) {
        QnaResponseDTO responseDTO = qnaService.createQna(qnaRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<QnaResponseDTO>> findQnaByItemId(@PathVariable Long itemId) {
        List<QnaResponseDTO> responseDTOList = qnaService.findQnaByItemId(itemId);
        return ResponseEntity.ok(responseDTOList);
    }

    @GetMapping("/user/{userNo}")
    public ResponseEntity<List<QnaResponseDTO>> findQnaByUserNo(@PathVariable Long userNo) {
        List<QnaResponseDTO> responseDTOList = qnaService.findQnaByUserNo(userNo);
        return ResponseEntity.ok(responseDTOList);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QnaResponseDTO> findQnaByQuestionId(@PathVariable Long questionId) {
        qnaService.findById(questionId);
        return ResponseEntity.ok(qnaService.findById(questionId));
    }

    @GetMapping("/list/{itemId}")
    public Page<QnaResponseDTO> findQnaByItemId(@PathVariable Long itemId, @PageableDefault(size = 5) Pageable pageable) {
        return qnaService.findQnaByPage(itemId, pageable);
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QnaResponseDTO> updateQna(@PathVariable Long questionId, @RequestBody UpdateQnaRequestDTO updateDTO) {
        QnaResponseDTO responseDTO = qnaService.updateQna(questionId, updateDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<QnaResponseDTO> deleteQna(@PathVariable Long questionId) {
        qnaService.deleteQna(questionId);
        return ResponseEntity.noContent().build();
    }
}
