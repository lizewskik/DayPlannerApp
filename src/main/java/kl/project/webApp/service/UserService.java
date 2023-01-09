package kl.project.webApp.service;

import kl.project.webApp.domain.User;
import kl.project.webApp.web.dto.UserRegistrationDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDTO userRegistrationDTO);
    User findById(Long id);
    UserRegistrationDTO findByEmail(String email);
    void update(UserRegistrationDTO registrationDTO);
    void delete(String email);
}
