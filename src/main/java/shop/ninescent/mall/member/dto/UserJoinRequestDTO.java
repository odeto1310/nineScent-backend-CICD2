package shop.ninescent.mall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
