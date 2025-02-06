package shop.ninescent.mall.qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.qna.dto.QnaRequestDTO;
import shop.ninescent.mall.qna.dto.QnaResponseDTO;
import shop.ninescent.mall.qna.domain.QnaBoard;
import shop.ninescent.mall.qna.dto.UpdateQnaRequestDTO;
import shop.ninescent.mall.qna.repository.QnaBoardRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaBoardRepository qnaBoardRepository;

    public QnaResponseDTO createQna(QnaRequestDTO qnaRequestDTO) {
        QnaBoard qnaBoard = QnaBoard.builder()
                .itemId(qnaRequestDTO.getItemId())
                .userNo(qnaRequestDTO.getUserNo())
                .qnaCategory(qnaRequestDTO.getQnaCategory())
                .content(qnaRequestDTO.getContent())
                .attachment(qnaRequestDTO.getAttachment())
                .isDone(false)
                .createdDate(LocalDateTime.now())
                .build();

        QnaBoard savedQnaBoard = qnaBoardRepository.save(qnaBoard);
        return toResponseDTO(savedQnaBoard);
    }

    public List<QnaResponseDTO> findQnaByItemId(Long itemId) {
        return qnaBoardRepository.findByItemId(itemId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<QnaResponseDTO> findById(Long questionId) {
        return qnaBoardRepository.findById(questionId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<QnaResponseDTO> findQnaByUserNo(Long userNo) {
        return qnaBoardRepository.findByUserNo(userNo).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public QnaResponseDTO updateQna(Long questionId, UpdateQnaRequestDTO updateDTO) {
        QnaBoard qnaBoard = qnaBoardRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 QnA가 존재하지 않습니다."));

        qnaBoard.setQnaCategory(updateDTO.getQnaCategory());
        qnaBoard.setContent(updateDTO.getContent());
        qnaBoard.setAttachment(updateDTO.getAttachment());

        QnaBoard updatedQnaBoard = qnaBoardRepository.save(qnaBoard);
        return toResponseDTO(updatedQnaBoard);
    }

    public void deleteQna(Long questionId) {
        QnaBoard qnaBoard = qnaBoardRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 QnA가 존재하지 않습니다"));

        qnaBoardRepository.delete(qnaBoard);
    }

    private QnaResponseDTO toResponseDTO(QnaBoard qnaBoard) {
        return QnaResponseDTO.builder()
                .questionId(qnaBoard.getQuestionId())
                .itemId(qnaBoard.getItemId())
                .userNo(qnaBoard.getUserNo())
                .qnaCategory(qnaBoard.getQnaCategory())
                .content(qnaBoard.getContent())
                .isDone(qnaBoard.isDone())
                .attachment(qnaBoard.getAttachment())
                .createdDate(qnaBoard.getCreatedDate())
                .build();
    }
}
