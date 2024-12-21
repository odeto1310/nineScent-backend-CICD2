package shop.ninescent.mall.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final SecureRandom random = new SecureRandom();

    // 인증 코드 생성
    @Override
    public String generateVerificationCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }

    // 인증 코드 이메일 전송
    @Override
    public void sendVerificationCode(String toEmail, String verificationCode) {
        String subject = "Ninescent Mall 인증 코드";
        String content = "<p>인증 코드입니다:</p><h3>" + verificationCode + "</h3>";
        sendEmail(toEmail, subject, content);
    }


    // 임시 비밀번호 이메일 전송
    @Override
    public void sendTempPasswordEmail(String toEmail, String temporaryPassword) {
        String subject = "Ninescent Mall 임시 비밀번호";
        String content = "<p>임시 비밀번호입니다. 로그인 후 비밀번호를 변경하세요:</p><h3>" + temporaryPassword + "</h3>";
        sendEmail(toEmail, subject, content);
    }

    // 이메일 전송 공통 로직
    @Override
    public void sendEmail(String toEmail, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true); // HTML 내용을 허용

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 발송 실패: " + e.getMessage(), e);
        }
    }
}
