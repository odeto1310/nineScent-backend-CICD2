package shop.ninescent.mall.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.ninescent.mall.member.domain.SocialAccount;

import java.util.List;
import java.util.Optional;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {

    // provider와 provider_id로 소셜 계정 조회
    Optional<SocialAccount> findByProviderAndProviderId(String provider, String provider_id);

    // 특정 사용자의 소셜 계정 모두 조회
    List<SocialAccount> findByUserUserNo(Long user_no);
}
