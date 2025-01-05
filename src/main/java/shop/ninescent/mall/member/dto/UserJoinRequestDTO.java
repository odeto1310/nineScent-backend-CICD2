package shop.ninescent.mall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.ninescent.mall.member.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserJoinRequestDTO {
    private String name; // 사용자 이름
    private String userId; // 사용자 ID (로그인용)
    private String password; // 비밀번호
    private String email; // 이메일

    private String address; // 선택적 주소
    private String phone; // 선택적 전화번호
    private String birth; // 생년월일 (yyyy-MM-dd 포맷)


    public User toEntity(String encodedPassword) {
        return User.builder()
                .name(this.name) // 사용자 이름 설정
                .userId(this.userId) // 사용자 ID 설정
                .password(encodedPassword) // 암호화된 비밀번호 설정
                .email(this.email) // 이메일 설정
                .address(this.address) // 주소 설정 (선택 사항)
                .phone(this.phone) // 전화번호 설정 (선택 사항)
                .birth(this.birth != null ? LocalDate.parse(this.birth) : null) // 생년월일 설정 (null 체크 후 파싱)
                .createdAt(LocalDateTime.now()) // 현재 시간으로 생성 시간 설정
                .role(User.Role.USER) // 기본 역할 USER 설정
                .build();
    }

}
