package bitcamp.show_pet.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String home() throws Exception {
    return "redirect:post/list";
  }
}
