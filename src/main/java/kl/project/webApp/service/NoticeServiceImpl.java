package kl.project.webApp.service;

import jakarta.transaction.Transactional;
import kl.project.webApp.domain.Notice;
import kl.project.webApp.domain.User;
import kl.project.webApp.repository.NoticeRepository;
import kl.project.webApp.repository.UserRepository;
import kl.project.webApp.web.dto.NoticeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @Override
    public void save(NoticeDTO noticeDTO) {
        User updated = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        Notice note = new Notice();
        note.setTitle(noticeDTO.getTitle());
        note.setBody(noticeDTO.getBody());
        note.setCreationTime(LocalDate.now());
        note.setDeadline(LocalDate.parse(noticeDTO.getDeadline()));
        noticeRepository.save(note);

        Collection<Notice> notes;
        if(updated.getNotes() == null){
            notes = new ArrayList<>();
        }else{
            notes = updated.getNotes();
        }
        notes.add(note);
        userRepository.save(updated);
    }

    @Override
    public Collection<NoticeDTO> findAll() {
        return userRepository
                .findByEmail(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName()
                )
                .getNotes()
                .stream()
                .sorted(Comparator.comparing(Notice::getDeadline))
                .map(notice -> {
                    NoticeDTO noticeDTO = new NoticeDTO();
                    noticeDTO.setId(notice.getId());
                    noticeDTO.setTitle(notice.getTitle());
                    noticeDTO.setBody(notice.getBody());
                    noticeDTO.setDeadline(notice.getDeadline().format(DateTimeFormatter.ISO_LOCAL_DATE));
                    return noticeDTO;
                })
                .sorted(new Comparator<NoticeDTO>() {
                    @Override
                    public int compare(NoticeDTO o1, NoticeDTO o2) {
                        return LocalDate.parse(o1.getDeadline()).compareTo(LocalDate.parse(o2.getDeadline()));
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        noticeRepository.deleteById(id);
    }

    @Override
    public NoticeDTO findById(Long id) {
        return noticeRepository.findById(id).map(note -> {
            NoticeDTO noticeDTO = new NoticeDTO();
            noticeDTO.setId(note.getId());
            noticeDTO.setTitle(note.getTitle());
            noticeDTO.setBody(note.getBody());
            noticeDTO.setDeadline(note.getDeadline().format(DateTimeFormatter.ISO_LOCAL_DATE));
            return noticeDTO;
        }).orElseThrow();
    }

    @Override
    public void update(NoticeDTO notice) {
        Notice updated = new Notice();
        updated.setId(notice.getId());
        updated.setTitle(notice.getTitle());
        updated.setBody(notice.getBody());
        updated.setDeadline(LocalDate.parse(notice.getDeadline()));
        noticeRepository.save(updated);
    }

    @Override
    public Collection<NoticeDTO> sortByDates(Collection<NoticeDTO> notes) {
        return notes.stream().sorted(new Comparator<NoticeDTO>() {
            @Override
            public int compare(NoticeDTO o1, NoticeDTO o2) {
                return LocalDate.parse(o1.getDeadline()).compareTo(LocalDate.parse(o2.getDeadline()));
            }
        }).collect(Collectors.toList());
    }
}
