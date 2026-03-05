    package streaming.util;

    import org.springframework.boot.CommandLineRunner;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Component;
    import streaming.model.User;
    import streaming.repository.UserRepository;

    @Component
    public class DataInitializer implements CommandLineRunner {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... args) {
            // Só cria os usuários se o banco estiver vazio
            if (userRepository.count() == 0) {
                User user = new User();
                user.setEmail("lucas@email.com");
                // Aqui a senha vira um hash seguro antes de ir para o H2
                user.setPassword(passwordEncoder.encode("123456")); 
                
                userRepository.save(user);
                System.out.println(">>> Banco H2 inicializado: Usuário lucas@email.com criado com sucesso!");
            }
        }
    }