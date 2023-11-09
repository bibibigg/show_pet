package bitcamp.show_pet.emoji.model.dao;

import bitcamp.show_pet.emoji.model.vo.Emoji;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmojiDao {

    void add(Emoji emoji);
    List<Emoji> getAll();
}
