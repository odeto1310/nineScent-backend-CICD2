package shop.ninescent.mall.qna.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.ninescent.mall.qna.dto.QnaRequestDTO;
import shop.ninescent.mall.qna.dto.QnaResponseDTO;
import shop.ninescent.mall.qna.service.QnaService;

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
}
