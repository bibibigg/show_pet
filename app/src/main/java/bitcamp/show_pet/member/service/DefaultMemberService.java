package bitcamp.show_pet.member.service;

import bitcamp.show_pet.member.model.dao.MemberDao;
import bitcamp.show_pet.member.model.vo.Member;
import bitcamp.show_pet.member.model.vo.Notification;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultMemberService implements MemberService {

  MemberDao memberDao;

  public DefaultMemberService(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Transactional
  @Override
  public int add(Member member) throws Exception {
    return memberDao.insert(member);
  }

  @Override
  public List<Member> list() throws Exception {
    return memberDao.findAll();
  }

  @Override
  public Member get(int memberId) throws Exception {
    return memberDao.findBy(memberId);
  }

  @Override
  public Member get(String email, String password) throws Exception {
    return memberDao.findByEmailAndPassword(email, password);
  }

  @Transactional
  @Override
  public int update(Member member) throws Exception {
    return memberDao.update(member);
  }

  @Transactional
  @Override
  public int delete(int memberId) throws Exception {
    return memberDao.delete(memberId);
  }


  @Override
  public boolean memberFollow(int currentMemberId, int memberId) throws Exception {
    boolean isFollowed = memberDao.isFollowed(currentMemberId, memberId);
    if (isFollowed) {
      memberDao.deleteFollow(currentMemberId, memberId);
    } else {
      memberDao.insertFollow(currentMemberId, memberId);
    }
    return !isFollowed;
  }

  @Override
  public boolean isFollowed(int currentMemberId, int memberId) throws Exception {
    return memberDao.isFollowed(currentMemberId, memberId);
  }

  @Override
  public Member get(int memberId, HttpSession session) throws Exception {
    Member member = memberDao.findBy(memberId);
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser != null) {
      int loggedInUserId = loginUser.getId();
      member.setFollowed(memberDao.isFollowed(loggedInUserId, memberId));
    } else {
      member.setFollowed(false);
    }
    return member;
  }

  @Override
  public List<Member> getFollowers(int memberId) throws Exception {
    return memberDao.getFollowers(memberId);
  }

  @Override
  public List<Member> getFollowings(int memberId) throws Exception {
    return memberDao.getFollowings(memberId);
  }

  @Override
  public List<Notification> getNotifications(int memberId) throws Exception {
    return memberDao.findNotificationsByMemberId(memberId);
  }

  @Override
  public void deleteAllNotifications(int memberId) throws Exception {
    memberDao.deleteAllNotifications(memberId);
  }

}
