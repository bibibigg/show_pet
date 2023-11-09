package bitcamp.show_pet.member.service;

import bitcamp.show_pet.member.model.vo.Notification;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {

  void send(String content, int receiverId);

  SseEmitter connectNotification(int memberId);

  void saveNotification(Notification notification);
}
