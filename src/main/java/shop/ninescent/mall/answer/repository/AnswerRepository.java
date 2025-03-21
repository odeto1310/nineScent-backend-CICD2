package shop.ninescent.mall.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.answer.domain.Answer;

import java.util.List;


public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionId(Long questionId);
}
