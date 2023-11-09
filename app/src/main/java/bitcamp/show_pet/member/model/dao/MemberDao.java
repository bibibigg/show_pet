package bitcamp.show_pet.member.model.dao;

import bitcamp.show_pet.member.model.vo.Member;
import bitcamp.show_pet.member.model.vo.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberDao {

    int insert(Member member);
    List<Member> findAll();
    Member findBy(int memberId);
    Member findByEmailAndPassword(
            @Param("email") String email,
            @Param("password") String password);
    int update (Member member);
    int delete(int no);

    void insertFollow(int followerId, int followingId);
    void deleteFollow(int followerId, int followingId);
    boolean isFollowed(int followerId, int followingId);
    List<Member> getFollowers(int memberId);
    List<Member> getFollowings(int memberId);

    int insertNotification(Notification notification);
    int updateReadStatus(int id, boolean isRead);
    List<Notification> findNotificationsByMemberId(int memberId);
    void deleteAllNotifications(int memberId) throws Exception;

}
