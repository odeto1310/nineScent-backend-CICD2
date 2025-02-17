package shop.ninescent.mall;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // exclude = 이 부분은 처음 톰캣 기동시 로그인 페이지 안뜨게 하려고

public class MallApplication {


	public static void main(String[] args) {
		SpringApplication.run(MallApplication.class, args);
	}



}
