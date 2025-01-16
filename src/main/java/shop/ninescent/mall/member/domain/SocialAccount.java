package shop.ninescent.mall.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "social_accounts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"provider", "provider_id"}) // 복합 유니크 제약 조건
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary Key

    @Column(nullable = false)
    private String provider; // 소셜 로그인 제공자 (e.g., Google, Naver)

    @Column(nullable = false)
    private String providerId; // 소셜 로그인 제공자의 고유 사용자 ID

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    private User user; // 연관된 User (Foreign Key)
}
