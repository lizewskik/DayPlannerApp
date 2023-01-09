package kl.project.webApp.web;

import jakarta.validation.Valid;
import kl.project.webApp.service.UserService;
import kl.project.webApp.web.dto.UserRegistrationDTO;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDTO userRegistrationDTO(){
        return new UserRegistrationDTO();
    }

    @GetMapping
    public String showRegistrationForm(){
        return "register";
    }

    @SneakyThrows
    @PostMapping
    public String registerUserAccount(@Valid @ModelAttribute("user") UserRegistrationDTO registrationDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "register";
        }
        userService.save(registrationDTO);
        return "redirect:/register?success";
    }
}
