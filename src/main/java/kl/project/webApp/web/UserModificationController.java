package kl.project.webApp.web;

import jakarta.validation.Valid;
import kl.project.webApp.service.UserService;
import kl.project.webApp.web.dto.UserRegistrationDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/change-account-details")
public class UserModificationController {
    private final UserService userService;

    public UserModificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/edit-account-form")
    public String showUserAccountDetailsForm(Model model) {
        model.addAttribute("user", userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "editAccount";
    }

    @PostMapping("/edit-account")
    public String changeUserAccountDetails(@Valid @ModelAttribute("user") UserRegistrationDTO registrationDTO, BindingResult bindingResult){
        UserRegistrationDTO existingUser = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        existingUser.setFirstName(registrationDTO.getFirstName());
        existingUser.setLastName(registrationDTO.getLastName());
        existingUser.setEmail(registrationDTO.getEmail());
        existingUser.setPassword(registrationDTO.getPassword());

        if(bindingResult.hasErrors()){
            return "editAccount";
        }else{
            try{
                userService.update(existingUser);
            }catch (DataIntegrityViolationException ex){
                bindingResult.rejectValue("email", "error.user", "An account already exists for this email.");
                return "editAccount";
            }
        }
        return "redirect:/change-account-details/edit-account-form?success";
    }

    @PostMapping("/delete-user")
    public String deleteUser() {
        userService.delete(SecurityContextHolder.getContext().getAuthentication().getName());
        //SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/logout";
    }
}
