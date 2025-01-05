package shop.ninescent.mall.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final SecureRandom random = new SecureRandom();

    // 인증 코드 생성
    @Override
    public String generateVerificationCode() {
        Random random = new Random();
        int verificationCode = 100000 + random.nextInt(900000);
        return String.valueOf(verificationCode);
    }

    // 인증 코드 이메일 전송
    @Override
    public void sendVerificationCode(String toEmail, String verificationCode) {
        String subject = "\uD83D\uDCEC FINmate - 이메일 인증코드";
        String content = "<html>" +
                "<head><style>h1, h2 { color: navy; } p { font-size: 16px; }</style></head>" +
                "<body>" +

                "<header><h1>FINmate</h1></header>" +
                "<section><p>아래 인증코드를 입력해 주세요:</p><h2>" + verificationCode + "</h2></section>" +
                "<footer></footer>" +
                "</body></html>";

        sendEmail(toEmail, subject, content);
    }


    // 임시 비밀번호 이메일 전송
    @Override
    public void sendTempPasswordEmail(String toEmail, String temporaryPassword) {
        String subject = "\uD83D\uDCED Ninescent Mall 임시 비밀번호";
        String content = "<html>" +
                "<head><style>h1, h2 { color: navy; } p { font-size: 16px; }</style></head>" +
                "<body>" +
                "<header><div><h1>FINmate</h1><div></header>" +
                "<section><p>아래 임시 비밀번호를 사용해 로그인해 주세요:</p><h2>" + temporaryPassword + "</h2></section>" +
                "<footer><div></div></footer>" +
                "</body></html>";

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
