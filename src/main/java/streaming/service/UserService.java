package streaming.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import streaming.model.User;
import streaming.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User cadastrar(String email, String password) {
        User user = new User();
        user.setEmail(email);
        
        user.setPassword(passwordEncoder.encode(password)); 
        
        return userRepository.save(user);
    }
}