package bitcamp.show_pet.reactionEmoji.service;

import bitcamp.show_pet.reactionEmoji.model.dao.ReactionEmojiDao;
import bitcamp.show_pet.reactionEmoji.model.vo.ReactionEmoji;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultReactionEmojiService implements ReactionEmojiService {

    ReactionEmojiDao reactionEmojiDao;

    public DefaultReactionEmojiService(ReactionEmojiDao reactionEmojiDao) {
        this.reactionEmojiDao = reactionEmojiDao;
    }

    @Override
    public void addReaction(ReactionEmoji reactionEmoji) throws Exception {
        reactionEmojiDao.addReaction(reactionEmoji);
    }

    @Override
    public List<ReactionEmoji> getReactionsForPost(int postId) throws Exception {
        return reactionEmojiDao.getReactionsForPost(postId);
    }

    @Override
    public void removeReaction(int memberId, int postId) throws Exception {
        reactionEmojiDao.removeReaction(memberId, postId);
    }
}
