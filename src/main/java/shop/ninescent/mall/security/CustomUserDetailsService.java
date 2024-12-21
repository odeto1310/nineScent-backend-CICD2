package shop.ninescent.mall.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.ninescent.mall.member.domain.User;
import shop.ninescent.mall.member.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        shop.ninescent.mall.member.domain.User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username"+username));

        // User 엔티티를 Spring Security의 UserDetails로 변환하여 반환
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId()) // Spring Security의 username 필드에 매핑
                .password(user.getPassword()) // Spring Security의 password 필드에 매핑
                .roles(user.getRole().name()) // 사용자 권한 (Enum -> String 변환)
                .build();
    }
}
