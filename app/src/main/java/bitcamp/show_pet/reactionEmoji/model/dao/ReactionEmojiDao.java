package bitcamp.show_pet.reactionEmoji.model.dao;

import bitcamp.show_pet.reactionEmoji.model.vo.ReactionEmoji;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReactionEmojiDao {

    void addReaction(ReactionEmoji reactionEmoji);

    List<ReactionEmoji> getReactionsForPost(int postId);

    void removeReaction(int memberId, int postId);
}
