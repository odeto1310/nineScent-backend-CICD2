package shop.ninescent.mall.mypage.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.address.domain.Address;
import shop.ninescent.mall.address.dto.AddressDTO;
import shop.ninescent.mall.address.service.AddressService;
import shop.ninescent.mall.mypage.dto.*;
import shop.ninescent.mall.mypage.service.MyPageService;

import java.util.List;

@RestController
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/address/{userNo}")
    public ResponseEntity<String> addOrUpdateAddress(@PathVariable Long userNo, @RequestBody AddressDTO addressDto) {
        Address address = addressService.addAddress(userNo, addressDto);
        if (address.getIsExtraFee()) {
            return ResponseEntity.ok("Address added with extra fee");
        } else {
            return ResponseEntity.ok("Address added successfully");
        }
    }
    @GetMapping("/addresses/{userNo}")
    public ResponseEntity<List<Address>> getAllAddresses(@PathVariable Long userNo) {
        return ResponseEntity.ok(addressService.getAddresses(userNo));
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