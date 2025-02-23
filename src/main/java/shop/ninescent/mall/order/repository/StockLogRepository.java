//package shop.ninescent.mall.order.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import shop.ninescent.mall.order.domain.StockLog;
//
//import java.util.Optional;
//
//@Repository
//public interface StockLogRepository extends JpaRepository<StockLog, Long> {
//    Optional<StockLog> findTopByItemItemIdAndChangeTypeOrderByCreatedAtDesc(Long itemId, String changeType);
//
//    Optional<StockLog> findByLogIdAndChangeType(Long logId, String changeType);
//
//}
