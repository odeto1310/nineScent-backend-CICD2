package shop.ninescent.mall.security.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequestDTO {
    private String userId; // 사용자 ID
    private String password; // 비밀번호
    private String email;
    private String name;

    public String getName() {
        return name;
    }

}
