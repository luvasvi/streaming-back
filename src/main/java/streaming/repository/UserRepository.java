package streaming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import streaming.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // O Spring Security usará este método para buscar o usuário no banco pelo e-mail
    Optional<User> findByEmail(String email);
}