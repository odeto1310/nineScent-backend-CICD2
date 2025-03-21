package shop.ninescent.mall.answer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.answer.dto.AnswerRequestDTO;
import shop.ninescent.mall.answer.dto.AnswerResponseDTO;
import shop.ninescent.mall.answer.dto.UpdateAnswerRequestDTO;
import shop.ninescent.mall.answer.service.AnswerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answer")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<AnswerResponseDTO> createAnswer(@RequestBody AnswerRequestDTO answerRequestDTO) {
        AnswerResponseDTO response = answerService.createAnswer(answerRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<AnswerResponseDTO>> getAnswerByQuestionId(@PathVariable Long questionId) {
        List<AnswerResponseDTO> responseDTOList = answerService.getAnswerByQuestionId(questionId);
        return ResponseEntity.ok(responseDTOList);
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerResponseDTO> getAnswerById(@PathVariable Long answerId) {
        answerService.getAnswerById(answerId);
        return ResponseEntity.ok(answerService.getAnswerById(answerId));
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<AnswerResponseDTO> deleteAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{answerId}")
    public ResponseEntity<AnswerResponseDTO> updateAnswer(@PathVariable Long answerId, @RequestBody UpdateAnswerRequestDTO updateDTO) {
        AnswerResponseDTO response = answerService.updateAnswer(answerId, updateDTO);
        return ResponseEntity.ok(response);
    }

}