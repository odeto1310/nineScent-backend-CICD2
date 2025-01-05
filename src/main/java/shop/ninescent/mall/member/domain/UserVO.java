package shop.ninescent.mall.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {

    private  Long userNo;      // Primary Key
    private  String name;      // 사용자 이름
    private  String password;
    private  String userId;    // 사용자 ID
    private  String email;     // 이메일
    private  String address;   // 선택적 주소
    private  String phone;     // 선택적 전화번호
    private  LocalDateTime createdAt; // 생성 시간
    private  LocalDate birth;      // 생년월일
    private  String role;      // 권한 (USER, ADMIN 등)

}
