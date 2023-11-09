package bitcamp.show_pet.mail;

import bitcamp.show_pet.member.model.vo.Member;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender javaMailSender;

  @Autowired
  private TemplateEngine templateEngine;

  public void sendWelcomeEmail(Member member) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

    // 이메일 내용을 Thymeleaf 템플릿으로 렌더링
    Context context = new Context();
    context.setVariable("member", member);
    String emailContent = templateEngine.process("mail/welcome", context);

    // 이메일 설정
    helper.setTo(member.getEmail());
    helper.setSubject("[Show Pet] 회원가입이 완료되었습니다.");
    helper.setText(emailContent, true);

    // 이메일 전송
    javaMailSender.send(mimeMessage);
  }
}
