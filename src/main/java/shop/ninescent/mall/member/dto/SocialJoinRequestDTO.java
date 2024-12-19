package shop.ninescent.mall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialJoinRequestDTO {
    private String provider;       // 소셜 로그인 제공자 (e.g., Google, Naver)
    private String providerId;     // 소셜 제공자가 발급한 고유 사용자 ID
    private String email;          // 사용자 이메일 (선택적으로 소셜에서 제공됨)
    private String name;           // 사용자 이름 (선택적으로 소셜에서 제공됨)
    private String phone;          // 전화번호 (추가 입력 필드)
    private String address;        // 주소 (추가 입력 필드)
}
