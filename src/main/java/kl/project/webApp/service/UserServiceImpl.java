package kl.project.webApp.service;

import kl.project.webApp.domain.Role;
import kl.project.webApp.domain.Roles;
import kl.project.webApp.domain.User;
import kl.project.webApp.repository.UserRepository;
import kl.project.webApp.web.dto.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(UserRegistrationDTO userRegistrationDTO) {

        User user = new User();
        user.setUserName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setRoles(Collections.singletonList(new Role(Roles.USER_ROLE.name())));
        user.setNotes(null);

        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public UserRegistrationDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        UserRegistrationDTO result =  new UserRegistrationDTO();
        result.setId(user.getId());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setEmail(user.getEmail());
        result.setPassword(user.getPassword());
        return result;
    }

    @Override
    public void update(UserRegistrationDTO registrationDTO) throws DataIntegrityViolationException {
        User updated = userRepository.findById(registrationDTO.getId()).get();
        updated.setUserName(registrationDTO.getFirstName());
        updated.setLastName(registrationDTO.getLastName());
        updated.setEmail(registrationDTO.getEmail());
        updated.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        userRepository.save(updated);
    }

    @Override
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username: " + username + " or password");
        }
        return user;
    }
}
