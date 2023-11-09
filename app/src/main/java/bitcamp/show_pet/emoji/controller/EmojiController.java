package bitcamp.show_pet.emoji.controller;

import bitcamp.show_pet.emoji.service.EmojiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/emoji")
public class EmojiController {

    public EmojiController(EmojiService emojiService) {
        this.emojiService = emojiService;
    }

    @Autowired
    EmojiService emojiService;

    @GetMapping("/test")
    public void getAll(Model model) throws Exception {
        model.addAttribute("getAll", emojiService.getAll());
    }
}
