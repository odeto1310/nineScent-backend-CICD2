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

    @PostMapping("/add")
    public ResponseEntity<String> addAddress(@RequestBody AddressDTO addressDTO, @RequestParam Long userNo) {
        Address address = addressService.addAddress(userNo, addressDTO);
        if (address.getIsExtraFee()) {
            return ResponseEntity.ok("Address added with extra fee");
        } else {
            return ResponseEntity.ok("Address added Successfully");
        }
    }

    // 기본 주소 가져오기
    @GetMapping("/default")
    public ResponseEntity<Address> getDefaultAddress(@RequestParam Long userNo) {
        Address defaultAddress = addressService.getDefaultAddress(userNo);
        return ResponseEntity.ok(defaultAddress);
    }

    // 사용자 모든 주소 가져오기
    @GetMapping("/list")
    public ResponseEntity<List<Address>> getAllAddresses(@RequestParam Long userNo) {
        List<Address> addresses = addressService.getAddresses(userNo);
        return ResponseEntity.ok(addresses);
    }

//    // 선택된 주소로 주문 페이지로 돌아가기
//    @PostMapping("/select")
//    public ResponseEntity<Address> selectAddressForOrder(@RequestBody Long addrNo, @RequestParam Long userNo) {
//        Address selectedAddress = addressService.getAddressByIdAndUserNo(addrNo, userNo);
//        return ResponseEntity.ok(selectedAddress);
//    }
}
