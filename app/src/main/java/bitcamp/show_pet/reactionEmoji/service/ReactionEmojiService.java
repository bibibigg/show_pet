package bitcamp.show_pet.reactionEmoji.service;

import bitcamp.show_pet.reactionEmoji.model.vo.ReactionEmoji;

import java.util.List;

public interface ReactionEmojiService {

    void addReaction(ReactionEmoji reactionEmoji) throws Exception;

    List<ReactionEmoji> getReactionsForPost(int postId) throws Exception;

    void removeReaction(int memberId, int postId) throws Exception;
}
