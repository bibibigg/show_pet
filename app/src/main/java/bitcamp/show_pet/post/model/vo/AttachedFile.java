package bitcamp.show_pet.post.model.vo;

import java.io.Serializable;

public class AttachedFile implements Serializable {

  private static final long serialVersionUID = 1L;

  int id;
  String originName;
  String filePath;
  int postId;

  @Override
  public String toString() {
    return "AttachedFile {" +
        "id=" + id +
        ", originName='" + originName + '\'' +
        ", filePath='" + filePath + '\'' +
        ", postId=" + postId +
        '}';
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOriginName() {
    return originName;
  }

  public void setOriginName(String originName) {
    this.originName = originName;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public int getPostId() {
    return postId;
  }

  public void setPostId(int postId) {
    this.postId = postId;
  }
}
