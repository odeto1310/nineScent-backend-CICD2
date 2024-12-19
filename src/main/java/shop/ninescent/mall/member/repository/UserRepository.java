package shop.ninescent.mall.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.member.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // user_id로 사용자 조회
    Optional<User> findByUserId(String user_id);

    // email로 사용자 조회
    Optional<User> findByEmail(String email);

    // provider와 provider_id로 소셜 계정 조회
    Optional<User> findByProviderAndProviderId(String provider, String provider_id);

    // user_id 중복 여부 확인
    boolean existsByUserId(String user_id);

    // email 중복 여부 확인
    boolean existsByEmail(String email);
}
