package streaming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streaming.model.FavoriteMovie;
import streaming.model.User;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<FavoriteMovie, Long> {
    List<FavoriteMovie> findByUser(User user);
    void deleteByMovieIdAndUser(Integer movieId, User user); 
}