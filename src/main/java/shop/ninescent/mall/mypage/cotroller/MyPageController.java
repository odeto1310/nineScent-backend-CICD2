//package shop.ninescent.mall.mypage.cotroller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import shop.ninescent.mall.address.domain.Address;
//import shop.ninescent.mall.address.dto.AddressDTO;
//import shop.ninescent.mall.address.service.AddressService;
//import shop.ninescent.mall.mypage.dto.MyPageDTO;
//import shop.ninescent.mall.mypage.service.MyPageService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/mypage")
//@RequiredArgsConstructor
//public class MyPageController {
//
//    private final MyPageService myPageService;
//
//    // 마이페이지 통합 조회 API
//    @GetMapping("/{userNo}")
//    public ResponseEntity<MyPageDTO> getMyPageOverview(@PathVariable Long userNo) {
//        MyPageDTO overview = myPageService.getMyPageOverview(userNo);
//        return ResponseEntity.ok(overview);
//    }
//}