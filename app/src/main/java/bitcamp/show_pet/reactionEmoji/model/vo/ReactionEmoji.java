package bitcamp.show_pet.reactionEmoji.model.vo;

import bitcamp.show_pet.emoji.model.vo.Emoji;
import bitcamp.show_pet.member.model.vo.Member;
import bitcamp.show_pet.post.model.vo.Post;

import java.io.Serializable;
import java.util.Objects;

public class ReactionEmoji implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private Member member;
    private Post post;
    private Emoji emoji;

    @Override
    public String toString() {
        return "ReactionEmoji{" +
                "id=" + id +
                ", member=" + member +
                ", post=" + post +
                ", emoji=" + emoji +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReactionEmoji reactionEmoji)) return false;
        return id == reactionEmoji.id && Objects.equals(member, reactionEmoji.member) && Objects.equals(post, reactionEmoji.post) && Objects.equals(emoji, reactionEmoji.emoji);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Emoji getEmoji() {
        return emoji;
    }

    public void setEmoji(Emoji emoji) {
        this.emoji = emoji;
    }
}
