package shop.ninescent.mall.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import shop.ninescent.mall.cartItem.domain.Cart;

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
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Prevent serialization of User's Cart
    private Cart cart; // Cart와 OneToOne 관계

    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }

    // 소셜 계정 연결 정보를 별도로 관리
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialAccount> socialAccounts = new ArrayList<>();

    public void addSocialAccount(SocialAccount socialAccount) {
        socialAccount.setUser(this);
        this.socialAccounts.add(socialAccount);
    }

    // UserVO로 변환
    public UserVO toVO() {
        return UserVO.builder()
                .userNo(this.userNo)
                .name(this.name)
                .userId(this.userId)
                .email(this.email)
                .address(this.address)
                .phone(this.phone)
                .createdAt(this.createdAt)
                .birth(this.birth)
                .role(this.role.name()) // Enum을 문자열로 변환
                .build();
    }

}
