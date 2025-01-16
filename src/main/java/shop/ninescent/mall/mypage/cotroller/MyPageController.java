package shop.ninescent.mall.mypage.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.mypage.dto.*;
import shop.ninescent.mall.mypage.service.MyPageService;

import java.util.List;

@RestController
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    @PostMapping("/address")
    public AddressDto addOrUpdateAddress(@RequestBody AddressDto addressDto) {
        return myPageService.addOrUpdateAddress(addressDto);
    }

    @GetMapping("/orders/{userNo}/status")
    public List<OrderDto> getShippingStatus(@PathVariable("userNo") Long userNo) {
        return myPageService.getShippingStatus(userNo);
    }

    @PostMapping("/orders/{orderId}/request")
    public String requestOrderChange(@PathVariable("orderId") Long orderId, @RequestBody OrderDto orderDto) {
        return myPageService.requestOrderChange(orderId, orderDto);
    }

    @PostMapping("/reviews")
    public ReviewDto writeReview(@RequestBody ReviewDto reviewDto) {
        return myPageService.writeReview(reviewDto);
    }

    @GetMapping("/reviews/{userNo}")
    public List<ReviewDto> getAllReviewsByUser(@PathVariable("userNo") Long userNo) {
        return myPageService.getAllReviewsByUser(userNo);
    }
}