package shop.ninescent.mall.mypage.repository;

import shop.ninescent.mall.mypage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
}
