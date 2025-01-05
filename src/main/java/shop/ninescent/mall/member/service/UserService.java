package shop.ninescent.mall.member.service;

import shop.ninescent.mall.member.domain.UserVO;
import shop.ninescent.mall.member.dto.*;
import shop.ninescent.mall.security.dto.UserLoginRequestDTO;

import java.util.Map;
import java.util.Optional;

public interface UserService {

    // 중복 확인
    boolean checkUsernameDuplicate(String username);
    boolean checkDuplicateEmail(String email);

    // 회원 정보 조회 및 업데이트
    UserVO get(String username);
    UserVO join(UserJoinRequestDTO member);
    String checkPasswordService(String password);

    // userId로 userNo 조회
    Long getMemberId(String userId);

    // 로그인된 사용자 삭제
    Optional<String> deleteLoggedInMember();

    // userId로 사용자 삭제
    boolean deleteMemberByUserId(String userId);

    String getLoggedInUsername();
    UserVO update(UserUpdateDTO member);
    void changePassword(ChangepasswordDTO changepasswordDTO);

    // ID/PW 찾기

    // ID 찾기
    UserVO findIdService(UserLoginRequestDTO member);
    String verifyIdService(VerifycodeDTO verifycodeDTO);
    String getPartialId(String id);

    // 비밀번호 찾기
    //PW 찾기
    VerifycodeDTO findPasswordService(VerifycodeDTO dto);

    Map<String, String> verifyPasswordService(VerifycodeDTO verifycodeDTO);
    boolean checkUserExistsByUsernameAndEmail(String username, String email);
    String generateTempPassword();
    void updateTempPassword(String username, String tempPassword);

    // 공통 기능
    boolean confirmVerificationCode(String inputCode, String actualCode);
    String getStoredVerificationCode(String email);
    void saveVerificationCode(String email, String code);
    void deleteVerificationCode(String email);
}
