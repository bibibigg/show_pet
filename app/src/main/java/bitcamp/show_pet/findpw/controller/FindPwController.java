package bitcamp.show_pet.findpw.controller;

import bitcamp.show_pet.findpw.service.FindPwResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class FindPwController {

  @Autowired
  FindPwResetService findPwResetService;

  @GetMapping("form")
  public void form() {
  }


  @GetMapping("/findpw/reset")
  public String showResetPasswordForm() {
    return "/findpw/reset";
  }

  @PostMapping("/findpw/reset")
  public String resetPassword(@RequestParam("email") String email,
      RedirectAttributes redirectAttributes) {
    try {
      findPwResetService.resetPassword(email);
      redirectAttributes.addFlashAttribute("successMessage", "임시 비밀번호가 이메일로 전송되었습니다.");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", "비밀번호 재설정 중 오류가 발생했습니다.");
    }
    return "redirect:/findpw/reset";
  }

}
