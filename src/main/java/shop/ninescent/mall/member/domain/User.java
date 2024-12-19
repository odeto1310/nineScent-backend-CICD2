package shop.ninescent.mall.member.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo; // Primary Key

    @Column(nullable = false)
    private String name; // 사용자 이름

    @Column(nullable = false, unique = true)
    private String userId; // 사용자 ID (로그인용)

    private String password; // 암호화된 비밀번호

    @Column(nullable = false, unique = true)
    private String email; // 이메일

    private String address; // 선택적 필드
    private String phone; // 선택적 필드

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성 시간

    private LocalDate birth; // 생년월일

    @Enumerated(EnumType.STRING)
    private Role role; // 사용자 권한

    public enum Role {
        USER, ADMIN
    }

    // 소셜 계정 연결 정보를 별도로 관리
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialAccount> socialAccounts = new ArrayList<>();

    public void addSocialAccount(SocialAccount socialAccount) {
        socialAccount.setUser(this);
        this.socialAccounts.add(socialAccount);
    }
}
