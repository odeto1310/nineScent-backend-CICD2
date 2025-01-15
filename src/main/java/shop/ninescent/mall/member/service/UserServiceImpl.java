package shop.ninescent.mall.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.ninescent.mall.email.EmailService;
import shop.ninescent.mall.member.domain.User;
import shop.ninescent.mall.member.domain.UserVO;
import shop.ninescent.mall.member.domain.VerificationCode;
import shop.ninescent.mall.member.dto.*;
import shop.ninescent.mall.member.repository.UserRepository;
import shop.ninescent.mall.member.repository.VerificationCodeRepository;
import shop.ninescent.mall.security.dto.UserLoginRequestDTO;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VerificationCodeRepository verificationCodeRepository;


    //Duplicate
    // username 중복 확인
    @Override
    public boolean checkUsernameDuplicate(String username) {
        return userRepository.existsByUserId(username);
    }

    //email 중복확인
    @Override
    public boolean checkDuplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // 회원 정보 조회 및 업데이트
    @Override
    public UserVO get(String username) {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자를 찾을 수 없습니다."));
        return user.toVO();
    }

    @Transactional
    @Override
    public UserVO join(UserJoinRequestDTO dto) {
        // 로그 추가 - DTO에 전달된 비밀번호 확인
        log.info("DTO에 전달된 비밀번호: {}", dto.getPassword());

        // 비밀번호가 null이면 예외를 발생시켜 원인 파악
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("비밀번호가 null이거나 빈 문자열입니다.");
        }
        // 중복 확인
        if (checkUsernameDuplicate(dto.getUserId()) || checkDuplicateEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디 또는 이메일입니다.");
        }
        // User 객체 생성 및 비밀번호 암호화
        User user = dto.toEntity(passwordEncoder.encode(dto.getPassword()));

        // Role 설정
//        user.setRole(User.Role.valueOf("ROLE_USER")); // 기본 사용자 권한 설정
        user.setRole(User.Role.ROLE_USER); // 기본 사용자 권한 설정
        // 사용자 정보 저장
        userRepository.save(user);

        // 삽입 후 userId가 제대로 설정되었는지 로그로 확인
        log.info("삽입 후 userId: {}", user.getUserId());

        // userId가 null인 경우 예외 발생
        if (user.getUserId() == null) {
            throw new IllegalStateException("userId가 설정되지 않았습니다.");
        }

        // 저장된 사용자 정보를 VO로 변환하여 반환
        return user.toVO();
    }


    @Override
    public String checkPasswordService(String password) {
        //현재 로그인한 사용자 username 가져오기
        String username = getLoggedInUsername();
        log.info("로그인한 사용자 ID :{} ", username);

        //사용자 정보 조회
        UserVO user = get(username);


        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("비밀번호 검증 실패 - 사용자 ID: {}", username);
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        log.info("비밀번호 검증 성공 - 사용자 ID: {}", username);
        return "비밀번호가 일치합니다.";
    }

    @Override
    public String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("getLoggedInUsername: {}", authentication.getName());
        return authentication.getName();
    }

    @Override
    public UserVO update(UserUpdateDTO dto) {
        User user = userRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // 사용자 정보 업데이트
        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getAddress() != null) {
            user.setAddress(dto.getAddress());
        }
        if (dto.getPhone() != null && !dto.getPhone().isBlank()) {
            user.setPhone(dto.getPhone());
        }
        if (dto.getBirth() != null) {
            user.setBirth(dto.getBirth());
        }

        // 변경된 사용자 정보 저장
        userRepository.save(user);

        // VO로 변환하여 반환
        return user.toVO();
    }

    @Override
    public void changePassword(ChangepasswordDTO changePasswordDTO) {
        User user = userRepository.findByUserId(changePasswordDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password does not match.");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);

        
    }




    // ID/PW 찾기

    //ID 찾기
    //FindId에서 호출할 로직
    @Override
    public UserVO findIdService(UserLoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getName())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        String verificationCode = emailService.generateVerificationCode();
        saveVerificationCode(dto.getEmail(), verificationCode);
        emailService.sendVerificationCode(dto.getEmail(), verificationCode);
        return user.toVO();
    }

    //아이디 인증 절차
    @Override
    public String verifyIdService(VerifycodeDTO verifycodeDTO) {
        //저장된 인증코드 가져오기
        String storedCode = getStoredVerificationCode(verifycodeDTO.getEmail());
        log.info("Stored Code: {}", storedCode);
        log.info("Input Code: {}", verifycodeDTO.getCode());

        //인증코드 검증 로직
        //인증코드 유효하지 않거나 만료된 경우 인증코드 삭제
        if (!confirmVerificationCode(verifycodeDTO.getCode(), storedCode)) {
            deleteVerificationCode(verifycodeDTO.getEmail());
            throw new IllegalArgumentException("인증코드가 일치하지 않습니다.");
        }


        // 이름과 이메일로 사용자 조회
        User user = userRepository.findByNameAndEmail(verifycodeDTO.getName(), verifycodeDTO.getEmail())
                .orElseThrow(() -> new NoSuchElementException("해당 사용자를 찾을 수 없습니다."));

        log.info("User 이름: {}, email: {}, 아이디: {}", user.getName(), user.getEmail(), user.getUserId());

        // 숨김 처리된 ID 반환
        String partialId = getPartialId(user.getUserId());
        log.info("숨김처리된 아이디: {}", partialId);

        return partialId;
    }

    //아이디 숨김처리
    @Override
    public String getPartialId(String id) {
        int idLength = id.length();

        // ID가 5글자인 경우: 첫 2글자와 마지막 2글자만 보여주고 중간 숨김
        if (idLength == 5) {
            String visibleStart = id.substring(0, 2);  // 첫 2자리
            String visibleEnd = id.substring(idLength - 2);  // 마지막 2자리
            String hiddenPart = "*";
            return visibleStart + hiddenPart + visibleEnd;
        }

        // ID가 5글자 미만인 경우: 첫 글자와 마지막 글자만 보여주고 나머지 숨김
        if (idLength < 5) {
            String visibleStart = id.substring(0, 1);  // 첫 글자
            String visibleEnd = id.substring(idLength - 1);  // 마지막 글자
            String hiddenPart = "*".repeat(idLength - 2);
            return visibleStart + hiddenPart + visibleEnd;
        }

        // ID가 6글자 이상인 경우: 앞 3자리와 마지막 2자리만 보여주고 나머지 숨김
        String visibleStart = id.substring(0, 3);
        String visibleEnd = id.substring(idLength - 2);
        String hiddenPart = "*".repeat(idLength - visibleStart.length() - visibleEnd.length());
        return visibleStart + hiddenPart + visibleEnd;
    }

    //PW 찾기
    @Override
    public VerifycodeDTO findPasswordService(VerifycodeDTO dto) {
        //Username과 email이 일치하는 회원정보가 있는지 확인
        boolean exists = checkUserExistsByUsernameAndEmail(dto.getUsername(), dto.getEmail());
        if (!exists) {
            throw new IllegalArgumentException("회원정보가 일치하지 않습니다.");
        }

        // 인증 코드 생성
        String verificationCode = emailService.generateVerificationCode();

        // 인증 코드 저장
        saveVerificationCode(dto.getEmail(), verificationCode);

        // 이메일로 인증 코드 발송
        emailService.sendVerificationCode(dto.getEmail(), verificationCode);

        // 인증 코드를 DTO에 설정
        dto.setCode(verificationCode);

        return dto;
    }

    //verifyPassword에서 호출할 service
    @Override
    public Map<String, String> verifyPasswordService(VerifycodeDTO verifycodeDTO) {
        //저장된 인증코드 가져오기
        String storedCode = getStoredVerificationCode(verifycodeDTO.getEmail());
        log.info("Stored Code: {}", storedCode);
        log.info("Input Code: {}", verifycodeDTO.getCode());

        // 인증 코드 확인
        boolean isValidCode = confirmVerificationCode(verifycodeDTO.getCode(), storedCode);
        if (!isValidCode) {
            log.warn("Verification failed for email: {}", verifycodeDTO.getEmail());
            deleteVerificationCode(verifycodeDTO.getEmail());
            return Map.of("error", "인증번호가 일치하지 않습니다. 인증코드를 다시 발급 받아주세요.");
        }

        // 임시 비밀번호 생성
        String tempPassword = generateTempPassword();
        log.info("Temporary password generated: {}", tempPassword);

        // DB에 임시 비밀번호 업데이트
        updateTempPassword(verifycodeDTO.getUsername(), tempPassword);
        log.info("Temporary password updated in DB for username: {}", verifycodeDTO.getUsername());

        // 임시 비밀번호 이메일 발송
        emailService.sendTempPasswordEmail(verifycodeDTO.getEmail(), tempPassword);
        log.info("Temporary password sent to email: {}", verifycodeDTO.getEmail());

        // 성공 메시지를 Map으로 반환
        return Map.of("message", "임시 비밀번호가 이메일로 발송되었습니다.");
    }

    //username과 email로 user 확인
    @Override
    public boolean checkUserExistsByUsernameAndEmail(String username, String email) {
        return userRepository.existsByUserIdAndEmail(username, email);
    }

    //임시 비밀번호 생성 메서드
    @Override
    public String generateTempPassword() {
        Random random = new Random();
        int length = 10;

        // 숫자와 문자만 포함 (ASCII 범위: 0-9(48-57), A-Z(65-90), a-z(97-122))
        String tempPassword = random.ints(48, 123)
                .filter(i -> (i >= 48 && i <= 57) || (i >= 65 && i <= 90) || (i >= 97 && i <= 122))  // 숫자, 대소문자 필터링
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        // 로그로 생성된 비밀번호 확인
        System.out.println("임시 비밀번호가 생성되었습니다: " + tempPassword);

        return tempPassword;
    }

    @Override
    public void updateTempPassword(String username, String tempPassword) {
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);
    }

    @Override
    public boolean confirmVerificationCode(String inputCode, String actualCode) {
        return inputCode.equals(actualCode);
    }


    //이메일 인증코드
    // 인증 코드 저장
    @Override
    public void saveVerificationCode(String email, String code) {
        log.info("인증 코드 저장 - 이메일: {}, 코드: {}", email, code);

        VerificationCode verificationCode = verificationCodeRepository.findById(email)
                .orElse(new VerificationCode()); // 데이터가 없으면 새 객체 생성

        verificationCode.setEmail(email);
        verificationCode.setCode(code);
        verificationCode.setCreatedAt(LocalDateTime.now());

        verificationCodeRepository.save(verificationCode); // 병합(Merge) 동작
    }


    // 이메일로 인증 코드 가져오기 (30분 내 유효한 코드만)
    @Override
    public String getStoredVerificationCode(String email) {
        log.info("인증 코드 조회 요청 - 이메일: {}", email);

        // 유효한 시간 기준 계산 (30분 이내)
        LocalDateTime validFrom = LocalDateTime.now().minusMinutes(30);

        // 인증 코드 조회
        return verificationCodeRepository.findValidCodeByEmail(email, validFrom)
                .orElseThrow(() -> new IllegalArgumentException("인증 코드가 만료되었거나 존재하지 않습니다."));
    }



    @Override
    public void deleteVerificationCode(String email) {
        log.info("인증 코드 삭제 요청 - 이메일: {}", email);

        // 리포지토리 메서드 호출
        verificationCodeRepository.deleteByEmail(email);

        log.info("인증 코드가 삭제되었습니다.");
    }


    // userId로 userNo 조회
    @Override
    public Long getMemberId(String userId) {
        return userRepository.findByUserId(userId)
                .map(User::getUserNo)
                .orElseThrow(() -> new RuntimeException("User not found for userId: " + userId));
    }

    // 로그인된 사용자 삭제
    @Override
    public Optional<String> deleteLoggedInMember() {
        String userId = getLoggedInUsername();
        log.info("회원탈퇴 요청 - 사용자: {}", userId);

        boolean isDeleted = deleteMemberByUserId(userId);

        if (isDeleted) {
            return Optional.of("회원탈퇴가 성공적으로 처리되었습니다.");
        } else {
            return Optional.empty();
        }
    }

    // userId로 사용자 삭제
    @Override
    public boolean deleteMemberByUserId(String userId) {
        try {
            userRepository.deleteByUserId(userId);
            log.info("회원 '{}' 탈퇴가 완료되었습니다.", userId);
            return true;
        } catch (Exception e) {
            log.error("회원 탈퇴 중 오류 발생 - 사용자: {}, 오류: {}", userId, e.getMessage());
            return false;
        }
    }



}
