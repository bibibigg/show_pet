package bitcamp.show_pet.emoji.model.vo;

import java.io.Serializable;
import java.util.Objects;

public class Emoji implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String emojiName;
    private String emojiUrl;

    public Emoji(String emojiName, String emojiUrl) {
        this.emojiName = emojiName;
        this.emojiUrl = emojiUrl;
    }

    @Override
    public String toString() {
        return "Emoji{" +
                "id=" + id +
                ", emojiName='" + emojiName + '\'' +
                ", emojiUrl='" + emojiUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Emoji emoji)) return false;
        return id == emoji.id && Objects.equals(emojiName, emoji.emojiName) && Objects.equals(emojiUrl, emoji.emojiUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, emojiName, emojiUrl);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmojiName() {
        return emojiName;
    }

    public void setEmojiName(String emojiName) {
        this.emojiName = emojiName;
    }

    public String getEmojiUrl() {
        return emojiUrl;
    }

    public void setEmojiUrl(String emojiUrl) {
        this.emojiUrl = emojiUrl;
    }
}
