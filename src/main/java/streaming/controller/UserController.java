package streaming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import streaming.model.User;
import streaming.repository.UserRepository;

@RestController
@RequestMapping("/login") // Bate com a liberação do seu SecurityConfig
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        // Criptografa a senha antes de salvar
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }
}