package shop.ninescent.mall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLoginResponseDTO {
    private String token;       // JWT 인증 토큰
    private String name;        // 사용자 이름
    private String email;       // 사용자 이메일
    private String provider;    // 소셜 로그인 제공자 (e.g., Google, Naver)
}
