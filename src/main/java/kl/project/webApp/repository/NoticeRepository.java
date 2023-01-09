package kl.project.webApp.repository;

import jakarta.transaction.Transactional;
import kl.project.webApp.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    void deleteById(Long id);
    void findAllById(Long id);
}
