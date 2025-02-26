package shop.ninescent.mall.qna.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.qna.domain.QnaBoard;

import java.util.List;

public interface QnaBoardRepository extends JpaRepository<QnaBoard, Long> {

    List<QnaBoard> findByItemId(Long itemId);

    Page<QnaBoard> findByItemId(Long itemId, Pageable pageable);

    List<QnaBoard> findByUserNo(Long userNo);
}
