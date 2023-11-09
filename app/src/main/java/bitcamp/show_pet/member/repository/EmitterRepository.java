package bitcamp.show_pet.member.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {

  private final Map<Integer, SseEmitter> emitterMap = new ConcurrentHashMap<>();

  public SseEmitter get(int memberId) {
    return emitterMap.get(memberId);
  }

  public void save(int memberId, SseEmitter emitter) {
    emitterMap.put(memberId, emitter);
  }

  public void delete(int memberId) {
    emitterMap.remove(memberId);
  }
}
