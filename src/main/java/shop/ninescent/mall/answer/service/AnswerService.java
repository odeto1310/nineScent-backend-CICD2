package shop.ninescent.mall.answer.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.answer.domain.Answer;
import shop.ninescent.mall.answer.dto.AnswerRequestDTO;
import shop.ninescent.mall.answer.dto.AnswerResponseDTO;
import shop.ninescent.mall.answer.dto.UpdateAnswerRequestDTO;
import shop.ninescent.mall.answer.repository.AnswerRepository;
import shop.ninescent.mall.qna.domain.QnaBoard;
import shop.ninescent.mall.qna.repository.QnaBoardRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QnaBoardRepository qnaRepository;

    @Transactional
    public AnswerResponseDTO createAnswer(AnswerRequestDTO answerRequestDTO) {
        QnaBoard qnaBoard = qnaRepository.findById(answerRequestDTO.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));

        Answer answerBoard = Answer.builder()
                .questionId(answerRequestDTO.getQuestionId())
                .answer(answerRequestDTO.getAnswer())
                .createdDate(LocalDateTime.now())
                .build();

        Answer savedAnswer = answerRepository.save(answerBoard);

        qnaBoard.setDone(true);
        qnaRepository.save(qnaBoard);

        return toResponse(savedAnswer);
    }

    public AnswerResponseDTO getAnswerById(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));

        return toResponse(answer);
    }

    @Transactional
    public List<AnswerResponseDTO> getAnswerByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));

        QnaBoard qnaBoard = qnaRepository.findById(answer.getQuestionId())
                        .orElseThrow(() -> new IllegalArgumentException("해당 문의가 존재하지 않습니다."));

        answerRepository.delete(answer);

        qnaBoard.setDone(false);
        qnaRepository.save(qnaBoard);
    }

    public  AnswerResponseDTO updateAnswer(Long answerId, UpdateAnswerRequestDTO updateAnswerRequestDTO) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));

        answer.setAnswer(updateAnswerRequestDTO.getAnswer());
        answer.setCreatedDate(LocalDateTime.now());

        Answer updatedAnswer = answerRepository.save(answer);
        return toResponse(updatedAnswer);
    }

    private AnswerResponseDTO toResponse(Answer answerBoard) {
        return AnswerResponseDTO.builder()
                .answerId(answerBoard.getAnswerId())
                .questionId(answerBoard.getQuestionId())
                .answer(answerBoard.getAnswer())
                .createdDate(answerBoard.getCreatedDate())
                .build();
    }
}
