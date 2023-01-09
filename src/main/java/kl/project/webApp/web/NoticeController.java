package kl.project.webApp.web;

import jakarta.validation.Valid;
import kl.project.webApp.service.NoticeService;
import kl.project.webApp.web.dto.NoticeDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/new-notice-form")
    public String showNewNoticeForm() {
        return "newNotice";
    }

    @ModelAttribute("notice")
    public NoticeDTO noticeDTO(){
        return new NoticeDTO();
    }

    @PostMapping ("/new-notice")
    public String createNotice(@Valid @ModelAttribute("notice") NoticeDTO noticeDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "newNotice";
        }
        noticeService.save(noticeDTO);
        return "redirect:/";
    }

    @PostMapping("/delete-notice/{id}")
    public String deleteNotice(@PathVariable Long id){
        noticeService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/edit-notice-form/{id}")
    public String showEditNoticeForm(@PathVariable Long id, Model model) {
        model.addAttribute("note", noticeService.findById(id));
        return "editNotice";
    }

    @RequestMapping("/edit-notice/{id}")
    public String editNotice(@Valid @ModelAttribute("note") NoticeDTO noticeDTO, @PathVariable Long id, BindingResult bindingResult) {
        NoticeDTO existingNote = noticeService.findById(id);
        existingNote.setTitle(noticeDTO.getTitle());
        existingNote.setBody(noticeDTO.getBody());
        existingNote.setDeadline(noticeDTO.getDeadline());

        if (bindingResult.hasErrors()){
            return "redirect:/edit-notice-form/" + id;
        }
        noticeService.update(existingNote);
        return "redirect:/";
    }
}
