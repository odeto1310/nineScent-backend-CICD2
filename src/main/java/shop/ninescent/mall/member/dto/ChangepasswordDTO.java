package shop.ninescent.mall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangepasswordDTO {
    private Long memberId;
    private String username;    //사용자 ID
    private String oldPassword; //이전 비밀번호
    private String newPassword; //새 비밀번호
}