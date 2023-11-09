package bitcamp.show_pet.post.model.vo;

import bitcamp.show_pet.member.model.vo.Member;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class Post implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final int 기타 = 1;
  public static final int 강아지 = 2;
  public static final int 고양이 = 3;
  public static final int 새 = 4;

  private int id;
  private Member member;
  private String title;
  private String content;
  private int viewCount;
  private String hashtag;
  private int category;
  private Timestamp createdAt;
  private List<AttachedFile> attachedFiles;
  private int likeCount;
  private boolean liked;
  private boolean isBookmarked;

  @Override
  public String toString() {
    return "Post{" +
        "id=" + id +
        ", member=" + member +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", viewCount=" + viewCount +
        ", hashtag='" + hashtag + '\'' +
        ", category=" + category +
        ", createdAt=" + createdAt +
        ", attachedFiles=" + attachedFiles +
        '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
      if (this == obj) {
          return true;
      }
      if (obj == null) {
          return false;
      }
      if (getClass() != obj.getClass()) {
          return false;
      }
    Post other = (Post) obj;
    return id == other.id;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getViewCount() {
    return viewCount;
  }

  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

  public String getHashTag() {
    return hashtag;
  }

  public void setHashTag(String hashtag) {
    this.hashtag = hashtag;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public List<AttachedFile> getAttachedFiles() {
    return attachedFiles;
  }

  public void setAttachedFiles(List<AttachedFile> attachedFiles) {
    this.attachedFiles = attachedFiles;
  }

  public int getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(int likeCount) {
    this.likeCount = likeCount;
  }

  public boolean isLiked() {
    return liked;
  }

  public void setLiked(boolean liked) {
    this.liked = liked;
  }

  public boolean isBookmarked() {
    return isBookmarked;
  }

  public void setBookmarked(boolean bookmarked) {
    isBookmarked = bookmarked;
  }
}
