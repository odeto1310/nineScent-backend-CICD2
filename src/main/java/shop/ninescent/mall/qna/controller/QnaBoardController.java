package shop.ninescent.mall.qna.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.qna.dto.QnaRequestDTO;
import shop.ninescent.mall.qna.dto.QnaResponseDTO;
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
}
