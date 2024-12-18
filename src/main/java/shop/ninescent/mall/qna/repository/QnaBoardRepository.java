package shop.ninescent.mall.qna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.qna.entity.QnaBoard;

import java.util.List;

public interface QnaBoardRepository extends JpaRepository<QnaBoard, Long> {

    List<QnaBoard> findByItemId(Long itemId);
}
