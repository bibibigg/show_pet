package bitcamp.show_pet.member.controller;

import bitcamp.show_pet.NcpObjectStorageService;
import bitcamp.show_pet.config.KakaoConfig;
import bitcamp.show_pet.config.NcpConfig;
import bitcamp.show_pet.mail.EmailService;
import bitcamp.show_pet.member.model.vo.Member;
import bitcamp.show_pet.member.model.vo.Notification;
import bitcamp.show_pet.member.model.vo.Role;
import bitcamp.show_pet.member.service.DefaultNotificationService;
import bitcamp.show_pet.member.service.MemberService;
import bitcamp.show_pet.post.service.PostService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequestMapping("/member")
public class MemberController {

  private final EmailService emailService;

  public MemberController(MemberService memberService, EmailService emailService) {
    this.memberService = memberService;
    this.emailService = emailService;
  }

  @Autowired
  NcpConfig ncpConfig;

  @Autowired
  MemberService memberService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @Autowired
  PostService postService;

  @Autowired
  private DefaultNotificationService defaultNotificationService;

  @GetMapping("form")
  public void form(@CookieValue(required = false) String email, Model model) {
    model.addAttribute("email", email);
  }

  // 로그인
  @PostMapping("login")
  public String login(
      String email,
      String password,
      String saveEmail,
      HttpSession session,
      Model model,
      HttpServletResponse response) throws Exception {

    if (saveEmail != null) {
      Cookie cookie = new Cookie("email", email);
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("email", "id");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

    Member loginUser = memberService.get(email, password);
    if (loginUser == null) {
      model.addAttribute("refresh", "1;url=form");
      throw new Exception("회원 정보가 일치하지 않습니다.");
    }

    session.setAttribute("loginUser", loginUser);

    if (loginUser.getRole() == Role.ADMIN) {
      System.out.println(loginUser.getRole());
      return "redirect:/admin/form";
    }
    return "redirect:/";
  }

  // 로그아웃
  @GetMapping("/logout")
  public String logout(HttpSession session) throws Exception {

    session.invalidate();
    return "redirect:/";
  }

  @GetMapping("join")
  public void join() {

  }

  // 회원가입
  @PostMapping("add")
  public String add(Member member) throws Exception {
    memberService.add(member);

    // 회원가입 이메일 전송
    emailService.sendWelcomeEmail(member);

    return "redirect:form";
  }

  @GetMapping("delete")
  public String delete(int id, Model model) throws Exception {
    if (memberService.delete(id) == 0) {
      model.addAttribute("refresh", "2;url=../post/list");
      throw new Exception("해당 회원이 없습니다.");
    }
    return "redirect:../post/list";
  }


  @GetMapping("list")
  public void list(Model model) throws Exception {
    model.addAttribute("list", memberService.list());
  }

  @GetMapping("profile/{memberId}")
  public String viewProfile(@PathVariable int memberId, Model model, HttpSession session)
      throws Exception {
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:/member/form";
    }
    List<Notification> notifications;
    if (loginUser.getId() == memberId) {
      notifications = memberService.getNotifications(memberId);
    } else {
      notifications = new ArrayList<>();
    }

    List<Member> followersList;
    List<Member> followingsList;

    if (loginUser.getId() == memberId) {
      followersList = memberService.getFollowers(loginUser.getId());
      followingsList = memberService.getFollowings(loginUser.getId());
    } else {
      followersList = memberService.getFollowers(memberId);
      followingsList = memberService.getFollowings(memberId);
    }

    model.addAttribute("followersList", followersList);
    model.addAttribute("followerCount", followersList.size());
    model.addAttribute("followingsList", followingsList);
    model.addAttribute("followingsCount", followingsList.size());

    model.addAttribute("member", memberService.get(memberId));
    model.addAttribute("myPosts", postService.getMyPosts(memberId));
    model.addAttribute("likedPosts", postService.getLikedPosts(memberId, session));
    model.addAttribute("bookMarkedPosts", postService.getBookmarkedPosts(memberId, session));
    model.addAttribute("notifications", notifications);

    return "member/profile";
  }

  @GetMapping("detail/{id}")
  public String detail(@PathVariable int id, Model model) throws Exception {
    model.addAttribute("member", memberService.get(id));
    return "member/detail";
  }


  @PostMapping("update")
  public String update(
      Member member,
      HttpSession session,
      MultipartFile photofile) throws Exception {

    if (photofile.getSize() > 0) {
      String uploadFileUrl = ncpObjectStorageService.uploadFile(
          "bitcamp-nc7-bucket-16", "member/", photofile);
      member.setPhoto(uploadFileUrl);
    } else {
      // 사용자가 사진을 업로드하지 않은 경우, 기존의 프로필 사진을 그대로 유지하도록 합니다.
      Member loginUser = (Member) session.getAttribute("loginUser");
      member.setPhoto(loginUser.getPhoto());
    }

    if (memberService.update(member) == 0) {
      throw new Exception("회원이 없습니다.");
    } else {
      session.setAttribute("loginUser", member);
      return "redirect:../post/list";
    }

  }

  @PostMapping("/{memberId}/follow")
  @ResponseBody
  public Map<String, Object> memberFollow(@PathVariable int memberId, HttpSession session)
      throws Exception {
    System.out.println("컨트롤러 멤버팔로우 호출됨!");
    Map<String, Object> response = new HashMap<>();
    Member loginUser = (Member) session.getAttribute("loginUser");

    if (loginUser == null) {
      response.put("status", "notLoggedIn");
      return response;
    }

    int currentMemberId = loginUser.getId();
    boolean newIsFollowed = memberService.memberFollow(currentMemberId, memberId);
    response.put("newIsFollowed", newIsFollowed);
    if (newIsFollowed) {
      Member member = memberService.get(memberId);
      if (member != null) {
        String content = loginUser.getNickName() + "님이 당신을 팔로우했습니다.";
        defaultNotificationService.send(content, member.getId());
      }
    }
    return response;
  }

  // 팔로우 상태 확인
  @PostMapping("/getFollowStatus")
  @ResponseBody
  public Map<Integer, Boolean> getFollowStatus(@RequestBody List<Integer> memberIds,
      HttpSession session)
      throws Exception {
    System.out.println("컨트롤러 팔로우상태확인 호출됨!");
    Member loginUser = (Member) session.getAttribute("loginUser");
    Map<Integer, Boolean> response = new HashMap<>();
    if (loginUser != null) {
      int currentMemberId = loginUser.getId();
      for (int memberId : memberIds) {
        boolean isFollowing = memberService.isFollowed(currentMemberId, memberId);
        response.put(memberId, isFollowing);
      }
    }
    return response;
  }

  @GetMapping("/followers")
  public String followers(HttpSession session, Model model) throws Exception {
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:/member/form";
    }
    List<Member> followersList = memberService.getFollowers(loginUser.getId());
    model.addAttribute("followersList", followersList);
    model.addAttribute("followerCount", followersList.size());
    return "member/followers";
  }

  @GetMapping("/followings")
  public String followings(HttpSession session, Model model) throws Exception {
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:/member/form";
    }
    List<Member> followingsList = memberService.getFollowings(loginUser.getId());
    model.addAttribute("followingsList", followingsList);
    model.addAttribute("followingsCount", followingsList.size()); // 팔로잉 숫자 추가
    return "member/followings";
  }

  @GetMapping("/notifications/stream")
  public SseEmitter streamNotifications(HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      // 로그인하지 않은 사용자에 대한 예외 처리
      throw new RuntimeException("로그인이 필요합니다.");
    }

    int memberId = loginUser.getId();
    return defaultNotificationService.connectNotification(memberId);
  }

  @PostMapping("/notifications/deleteAll")
  public ResponseEntity<?> deleteAllNotifications(HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return new ResponseEntity<>("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
    }

    try {
      memberService.deleteAllNotifications(loginUser.getId());
      return new ResponseEntity<>("모든 알림이 삭제되었습니다.", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/headerNotifications")
  public ResponseEntity<List<Notification>> getHeaderNotifications(HttpSession session) {
    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    try {
      List<Notification> notifications = memberService.getNotifications(loginUser.getId());
      return new ResponseEntity<>(notifications, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}

