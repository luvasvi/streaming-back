package streaming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import streaming.model.FavoriteMovie;
import streaming.model.User;
import streaming.repository.FavoriteRepository;
import streaming.repository.UserRepository;
import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<FavoriteMovie> getFavorites(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        return favoriteRepository.findByUser(user);
    }

    @PostMapping("/add")
    public FavoriteMovie addFavorite(@RequestBody FavoriteMovie movie, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        movie.setUser(user);
        return favoriteRepository.save(movie);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> removeFavorite(@PathVariable Integer movieId, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        favoriteRepository.deleteByMovieIdAndUser(movieId, user);
        return ResponseEntity.ok().build();
    }
}