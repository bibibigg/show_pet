package bitcamp.show_pet.emoji.service;

import bitcamp.show_pet.emoji.model.dao.EmojiDao;
import bitcamp.show_pet.emoji.model.vo.Emoji;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Service
public class DefaultEmojiService implements EmojiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final EmojiDao emojiDao;

    public DefaultEmojiService(EmojiDao emojiDao) {
        this.emojiDao = emojiDao;
        fetchAndStoreEmojis();
    }

    @PostConstruct
    public void fetchAndStoreEmojis() {
        List<Emoji> emojis = emojiDao.getAll();
        if (emojis.isEmpty()) {
            // 데이터베이스가 비어있는 경우에만 데이터를 가져와서 저장합니다.
            Map<String, String> emojiMap = restTemplate.getForObject("https://api.github.com/emojis", Map.class);

            for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
                Emoji emoji = new Emoji(entry.getKey(), entry.getValue());
                emojiDao.add(emoji);
            }
        }
    }

    @Override
    public List<Emoji> getAll() throws Exception {
        return emojiDao.getAll();
    }
}
