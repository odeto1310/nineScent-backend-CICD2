package shop.ninescent.mall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long userNo; // 사용자 고유 번호
    private String name; // 사용자 이름
    private String userId; // 사용자 ID
    private String email; // 이메일
    private String address; // 주소
    private String phone; // 전화번호
    private String birth; // 생년월일 (yyyy-MM-dd 포맷)
}
