package streaming.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data // Gera Getters e Setters automaticamente
@NoArgsConstructor // Construtor padrão para o JPA
@AllArgsConstructor // Construtor com todos os campos
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ElementCollection
    private List<Long> favoriteMovieIds = new ArrayList<>();
}