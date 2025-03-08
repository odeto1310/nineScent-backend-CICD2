package shop.ninescent.mall.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.ninescent.mall.member.domain.UserVO;
import shop.ninescent.mall.member.dto.ChangepasswordDTO;
import shop.ninescent.mall.member.dto.UserJoinRequestDTO;
import shop.ninescent.mall.member.dto.UserUpdateDTO;
import shop.ninescent.mall.member.dto.VerifycodeDTO;
import shop.ninescent.mall.member.service.UserService;

import java.util.*;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // Username 중복 확인 - 인증x
    @GetMapping("/checkusername/{username}")
    public ResponseEntity<Boolean> checkUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.checkUsernameDuplicate(username));
    }

    // Email 중복 확인 - 인증x
    @GetMapping("/checkemail/{email}")
    public ResponseEntity<Boolean> checkEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.checkDuplicateEmail(email));
    }

    // 회원 가입
    @PostMapping("/join")
    public ResponseEntity<UserVO> join(@RequestBody UserJoinRequestDTO dto) {
        log.info("회원 가입 요청 - 사용자 ID: {}", dto.getUserId());
        return ResponseEntity.ok(userService.join(dto));
    }

    // 회원 탈퇴
    @DeleteMapping("/delete-member")
    public ResponseEntity<Map<String, String>> deleteLoggedInMember() {
        Optional<String> result = userService.deleteLoggedInMember();
        return result.map(msg -> ResponseEntity.ok(Map.of("message", msg)))
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "회원 탈퇴 처리 중 문제가 발생했습니다.")));
    }

    // 비밀번호 확인 - 인증x
    @PostMapping("/checkpassword")
    public ResponseEntity<Map<String, String>> checkPassword(@RequestBody Map<String, String> map) {
        Optional<String> result = Optional.ofNullable(userService.checkPasswordService(map.get("password")));
        return result.map(msg -> ResponseEntity.ok(Map.of("message", msg)))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "비밀번호가 일치하지 않습니다.")));
    }

    // 회원 정보 수정
    @PutMapping("/{username}")
    public ResponseEntity<UserVO> updateUser(@PathVariable String username, @RequestBody UserUpdateDTO dto) {
        log.info("회원 정보 수정 요청 - 사용자 ID: {}", username);
        dto.setUserId(username);
        return ResponseEntity.ok(userService.update(dto));
    }

    // 비밀번호 변경
    @PutMapping("/{username}/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangepasswordDTO dto) {
        userService.changePassword(dto);
        return ResponseEntity.ok().build();
    }

    // ID 찾기 - 인증x
    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestBody VerifycodeDTO dto) {
        String partialId = userService.verifyIdService(dto);
        return ResponseEntity.ok(partialId);
    }

    // 비밀번호 찾기 - 인증x
    @PostMapping("/find-password")
    public ResponseEntity<Map<String, String>> findPassword(@RequestBody VerifycodeDTO dto) {
        Map<String, String> result = userService.verifyPasswordService(dto);
        if (result.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.ok(result);
    }


    // ID찾기 인증 코드 확인 - 인증x
    @PostMapping("/verify-id")
    public ResponseEntity<String> verifyId(@RequestBody VerifycodeDTO verifycodeDTO) {
        log.info("ID 인증 요청 - 인증 코드: {}", verifycodeDTO.getCode());
        Optional<String> partialId = Optional.ofNullable(userService.verifyIdService(verifycodeDTO));
        return partialId.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 비밀번호 찾기 인증 코드 확인 - 인증x
    @PostMapping("/verify-password")
    public ResponseEntity<Map<String, String>> verifyPassword(@RequestBody VerifycodeDTO verifycodeDTO) {
        Optional<Map<String, String>> result = Optional.ofNullable(userService.verifyPasswordService(verifycodeDTO));
        return result
                .filter(map -> !map.containsKey("error")) // 에러가 없는 경우에만 성공 처리
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "인증번호가 일치하지 않거나 오류가 발생했습니다.")));
    }

    // userId로 userNo 조회 - 인증x
    @GetMapping("/getMemberId/{username}")
    public ResponseEntity<Long> getMemberId(@PathVariable String username) {
        try {
            Long memberId = userService.getMemberId(username);
            return ResponseEntity.ok(memberId);
        } catch (Exception e) {
            log.error("회원 ID 조회 실패 - 사용자: {}, 오류: {}", username, e.getMessage());
            throw new RuntimeException("회원 ID 조회 중 오류 발생");
        }
    }

}
