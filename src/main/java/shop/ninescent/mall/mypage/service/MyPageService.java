package shop.ninescent.mall.mypage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.mypage.dto.OrderDto;
import shop.ninescent.mall.mypage.dto.ReviewDto;
import shop.ninescent.mall.mypage.repository.OrderRepository;
import shop.ninescent.mall.mypage.repository.ReviewRepository;

import java.util.List;

@Service
public class MyPageService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<OrderDto> getShippingStatus(Long userNo) {
        // Implementation
        return null;
    }

    public String requestOrderChange(Long orderId, OrderDto orderDto) {
        // Implementation
        return "Request processed successfully.";
    }

    public ReviewDto writeReview(ReviewDto reviewDto) {
        // Implementation
        return null;
    }

    public List<ReviewDto> getAllReviewsByUser(Long userNo) {
        // Implementation
        return null;
    }
}
