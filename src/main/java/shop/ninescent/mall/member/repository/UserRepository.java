package shop.ninescent.mall.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.member.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // user_id로 사용자 조회
    Optional<User> findByUserId(String userId);

    // email로 사용자 조회
    Optional<User> findByEmail(String email);

    // provider와 provider_id로 소셜 계정 조회
//    Optional<User> findByProviderAndProviderId(String provider, String providerId);

    // user_id 중복 여부 확인
    boolean existsByUserId(String userId);

    // email 중복 여부 확인
    boolean existsByEmail(String email);
}
