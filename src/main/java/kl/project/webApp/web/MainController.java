package kl.project.webApp.web;

import kl.project.webApp.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final NoticeService noticeService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("notes", noticeService.findAll());
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
