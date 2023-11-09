package bitcamp.show_pet.findpw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DefaultFindPwService {
    @Autowired
    JavaMailSender javaMailSender;

    public void sendTemporaryPasswordEmail(String toEmail, String temporaryPassword) {
        // 임시 비밀번호를 포함한 이메일 내용 생성
        String subject = "[Show Pet] 임시 비밀번호 발송";
        String text = "[Show Pet] 임시 비밀번호 : " + temporaryPassword + "\n" + " 마이페이지에서 비밀번호를 변경해주세요.";

        // 이메일 메시지 생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        // 이메일 보내기
        javaMailSender.send(message);
    }
}
