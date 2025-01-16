package shop.ninescent.mall.email;

public interface EmailService {

    //인증코드 생성
    String generateVerificationCode();

    //인증코드 이메일 전송
    void sendVerificationCode(String toEmail, String verificationCode);

    //임시 비밀번호 이메일 전송
    void sendTempPasswordEmail(String toEmail, String tempPassword);

    //이메일 전송 공통 로직
    void sendEmail(String toEmail, String subject, String content);


}
