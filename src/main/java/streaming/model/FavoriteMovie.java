package streaming.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "favorite_movies")
public class FavoriteMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer movieId;
    private String title;
    private String posterPath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}