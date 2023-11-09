package bitcamp.show_pet.reactionEmoji.controller;

import bitcamp.show_pet.config.NcpConfig;
import bitcamp.show_pet.reactionEmoji.model.dao.ReactionEmojiDao;
import bitcamp.show_pet.reactionEmoji.model.vo.ReactionEmoji;
import bitcamp.show_pet.reactionEmoji.service.ReactionEmojiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reaction")
public class ReactionEmojiController {

    private final ReactionEmojiService reactionEmojiService;

    public ReactionEmojiController(ReactionEmojiService reactionEmojiService) {
        this.reactionEmojiService = reactionEmojiService;
    }

    @PostMapping("/")
    public ResponseEntity<List<ReactionEmoji>> addReaction(@RequestBody ReactionEmoji reaction) throws Exception {
        reactionEmojiService.addReaction(reaction);
        List<ReactionEmoji> reactions = reactionEmojiService.getReactionsForPost(reaction.getPost().getId());
        return new ResponseEntity<>(reactions, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<ReactionEmoji>> getReactionsForPost(@PathVariable("postId") int postId) throws Exception {
        List<ReactionEmoji> reactions = reactionEmojiService.getReactionsForPost(postId);
        return new ResponseEntity<>(reactions, HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}/{postId}")
    public ResponseEntity<Void> removeReaction(@PathVariable int memberId,@PathVariable int postId) throws Exception {
        reactionEmojiService.removeReaction(memberId, postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
