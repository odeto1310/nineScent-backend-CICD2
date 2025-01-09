package shop.ninescent.mall.mypage.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import shop.ninescent.mall.mypage.dto.AddressDto;
import shop.ninescent.mall.mypage.dto.OrderDto;
import shop.ninescent.mall.mypage.dto.ReviewDto;
import shop.ninescent.mall.mypage.entity.Address;
import shop.ninescent.mall.mypage.entity.Order;
import shop.ninescent.mall.mypage.entity.Review;
import shop.ninescent.mall.mypage.entity.User;
import shop.ninescent.mall.mypage.repository.AddressRepository;
import shop.ninescent.mall.mypage.repository.OrderRepository;
import shop.ninescent.mall.mypage.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyPageService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public AddressDto addOrUpdateAddress(AddressDto addressDto) {
        Address address = new Address();
        // Convert DTO to Entity
        address.setUserNo(addressDto.getUserNo());
        address.setAddrName(addressDto.getAddrName());
        // ... Set other fields
        Address savedAddress = addressRepository.save(address);
        // Convert Entity to DTO
        AddressDto savedDto = new AddressDto();
        savedDto.setAddrNo(savedAddress.getAddrNo());
        savedDto.setAddrName(savedAddress.getAddrName());
        // ... Set other fields
        return savedDto;
    }

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
