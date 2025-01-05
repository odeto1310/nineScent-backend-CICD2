package shop.ninescent.mall.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.order.domain.StockLog;

import java.util.Optional;


public interface StockLogRepository extends JpaRepository<StockLog, Long> {
    Optional<StockLog> findTopByItemIdAndChangeTypeOrderByCreatedAtDesc(Long itemId, String changeType);
    Optional<StockLog> findByLogIdAndChangeType(Long logId, String changeType);

}
