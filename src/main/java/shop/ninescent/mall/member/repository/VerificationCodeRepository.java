package shop.ninescent.mall.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.ninescent.mall.member.domain.VerificationCode;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, String> {

    @Query("SELECT v.code FROM VerificationCode v WHERE v.email = :email AND v.createdAt >= :validFrom")
    Optional<String> findValidCodeByEmail(@Param("email") String email, @Param("validFrom") LocalDateTime validFrom);

//    VerificationCode findByEmail(String email);

    void deleteByEmail(String email);
}
