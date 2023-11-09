package bitcamp.show_pet.emoji.service;

import bitcamp.show_pet.emoji.model.vo.Emoji;

import java.util.List;

public interface EmojiService {
    void fetchAndStoreEmojis();
    List<Emoji> getAll() throws Exception;
}
