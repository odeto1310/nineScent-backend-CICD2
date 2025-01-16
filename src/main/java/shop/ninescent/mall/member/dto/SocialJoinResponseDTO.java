package shop.ninescent.mall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialJoinResponseDTO {
    private Long userNo;        // 사용자 고유 번호
    private String name;        // 사용자 이름
    private String email;       // 사용자 이메일
    private String provider;    // 소셜 로그인 제공자 (e.g., Google, Naver)
    private String providerId;  // 소셜 제공자의 고유 사용자 ID
}
