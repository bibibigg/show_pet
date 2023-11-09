package bitcamp.show_pet.findpw.model.vo;

import java.io.Serializable;

public class FindPw implements Serializable {

  private static final long serialVersionUID = 1L;

  private int userId;
  private String username;
  private String email;

  // 생성자, 게터, 세터, toString 등의 메서드

  // 생성자
  public FindPw(int userId, String username, String email) {
    this.userId = userId;
    this.username = username;
    this.email = email;
  }

  // 게터와 세터 메서드
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  // toString 메서드
  @Override
  public String toString() {
    return "UserVO{" +
        "userId=" + userId +
        ", username='" + username + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
