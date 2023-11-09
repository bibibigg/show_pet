package bitcamp.show_pet.member.model.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Member implements Serializable {

  public static final long serialVersionUID = 1L;

  public static final int 기타 = 1;
  public static final int 강아지 = 2;
  public static final int 고양이 = 3;
  public static final int 새 = 4;

  private int id;
  private Role role;
  private char activation;
  private String email;
  private String password;
  private String nickName;
  private String photo;
  private String intro;
  private String tel;
  private int category;

  private boolean isFollowed;
  private List<Member> followers;
  private List<Member> followings;

  @Override
  public String toString() {
    return "Member{" +
        "id=" + id +
        ", role=" + role +
        ", activation=" + activation +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", nickName='" + nickName + '\'' +
        ", photo='" + photo + '\'' +
        ", intro='" + intro + '\'' +
        ", tel='" + tel + '\'' +
        ", category=" + category +
        ", isFollowed=" + isFollowed +
        ", followers=" + followers +
        ", followings=" + followings +
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
    Member other = (Member) obj;
    return id == other.id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public char getActivation() {
    return activation;
  }

  public void setActivation(char activation) {
    this.activation = activation;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public boolean isFollowed() {
    return isFollowed;
  }

  public void setFollowed(boolean followed) {
    isFollowed = followed;
  }

  public List<Member> getFollowers() {
    return followers;
  }

  public void setFollowers(List<Member> followers) {
    this.followers = followers;
  }

  public List<Member> getFollowings() {
    return followings;
  }

  public void setFollowings(List<Member> followings) {
    this.followings = followings;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }
}
