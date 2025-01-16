package shop.ninescent.mall.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="verification_code")
public class VerificationCode {
    @Id
    @Column(length = 100)
    private String email;

    @Column(length = 10, nullable = false)
    private String code;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
