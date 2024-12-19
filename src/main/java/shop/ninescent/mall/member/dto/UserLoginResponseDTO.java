package shop.ninescent.mall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginResponseDTO {
    private String token;  // JWT 토큰
    private Long userNo;   // 사용자 고유 번호
    private String name;   // 사용자 이름
    private String userId; // 사용자 ID
    private String email;  // 사용자 이메일
}
