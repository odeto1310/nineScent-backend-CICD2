package shop.ninescent.mall.address.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.address.domain.Address;
import shop.ninescent.mall.address.dto.AddressDTO;
import shop.ninescent.mall.address.service.AddressService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    // 주소 추가
    @PostMapping("/add/{userNo}")
    public ResponseEntity<String> addAddress(@RequestBody AddressDTO addressDTO, @PathVariable Long userNo) {
        Address address = addressService.addAddress(userNo, addressDTO);
        if (address.getIsExtraFee()) {
            return ResponseEntity.ok("Address added with extra fee");
        } else {
            return ResponseEntity.ok("Address added Successfully");
        }
    }
    // test
    //{
    //"addrName": "Home2",
    //"addrContact": "010-1234-5678",
    //"addrZipcode": "15655",
    //"addrAddress": "Seoul, Korea",
    //"addrDetail": "101동 202호",
    //"addrRequest": ""
    //}


    // 기본 주소 가져오기
    @GetMapping("/default/{userNo}")
    public ResponseEntity<Address> getDefaultAddress(@PathVariable Long userNo) {
        Address defaultAddress = addressService.getDefaultAddress(userNo);
        return ResponseEntity.ok(defaultAddress);
    }

    // 사용자 모든 주소 가져오기
    @GetMapping("/list/{userNo}")
    public ResponseEntity<List<Address>> getAllAddresses(@PathVariable Long userNo) {
        List<Address> addresses = addressService.getAddresses(userNo);
        return ResponseEntity.ok(addresses);
    }
    // 주소 수정
    @PutMapping("update/{addrNo}")
    public ResponseEntity<String> updateAddress(@PathVariable Long addrNo, @RequestBody AddressDTO addressDTO) {
        addressService.updateAddress(addrNo, addressDTO);
        return ResponseEntity.ok("Address updated successfully");
    }
    // 주소 삭제
    @DeleteMapping("delete/{addrNo}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addrNo) {
        addressService.deleteAddress(addrNo);
        return ResponseEntity.ok("Address deleted successfully");
    }

    // isLiked 값 변경
    @PutMapping("/like/{addrNo}")
    public ResponseEntity<String> likeAddress(@PathVariable Long addrNo) {
        addressService.setLikedAddress(addrNo);
        return ResponseEntity.ok("Address like successfully");
    }

    // isDefault 값 변경
    @PutMapping("default/{addrNo}")
    public ResponseEntity<String> defaultAddress(@PathVariable Long addrNo, @RequestParam Long userNo) {
        addressService.setDefaultAddress(addrNo, userNo);
        return ResponseEntity.ok("Default address successfully");
    }


}
