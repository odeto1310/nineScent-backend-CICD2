package shop.ninescent.mall.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ninescent.mall.order.domain.Orders;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    // 📌 주문 상태별 개수 조회
    long countByUser_UserNoAndPaymentDoneFalseAndOrderDateAfter(Long userNo, LocalDateTime oneMonthAgo);  // 입금 대기 중
    long countByUser_UserNoAndDeliveryDoneFalseAndPaymentDoneTrueAndOrderDateAfter(Long userNo, LocalDateTime oneMonthAgo); // 배송 준비 중
//    long countByUser_UserNoAndDeliveryDoneFalseAndOrderDateAfter(Long userNo, LocalDateTime oneMonthAgo); // 배송 중
    long countByUser_UserNoAndDeliveryDoneTrueAndOrderDateAfter(Long userNo, LocalDateTime oneMonthAgo); // 배송 완료
    long countByUser_UserNoAndRefundChangeDoneTrueAndOrderDateAfter(Long userNo, LocalDateTime oneMonthAgo); // 취소 / 반품 / 교환

    // 최근 1개월 주문 조회
    List<Orders> findByUser_UserNoAndOrderDateAfter(Long userNo, LocalDateTime oneMonthAgo, Pageable pageable);

    // 전체 주문 내역 조회 (페이징)
    Page<Orders> findByUser_UserNo_OrderByOrderIdDesc(Long userNo, Pageable pageable);
}