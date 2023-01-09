package kl.project.webApp.service;

import kl.project.webApp.web.dto.NoticeDTO;

import java.util.Collection;

public interface NoticeService {
    void save(NoticeDTO noticeDTO);
    Collection<NoticeDTO> findAll();
    void deleteById(Long id);
    NoticeDTO findById(Long id);
    void update(NoticeDTO notice);
    Collection<NoticeDTO> sortByDates(Collection<NoticeDTO> notes);
}
