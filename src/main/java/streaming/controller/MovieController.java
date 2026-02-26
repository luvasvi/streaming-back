package streaming.controller;

import org.springframework.web.bind.annotation.*;
import streaming.dto.MovieDTO;
import streaming.dto.MovieDetailsDTO;
import streaming.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Retorna os filmes/séries que estão bombando (Trending).
     * Agora aceita parâmetro de página.
     */
    @GetMapping("/trending")
    public List<MovieDTO> getTrending(@RequestParam(defaultValue = "1") int page) {
        return movieService.getTrendingMovies(page);
    }

    /**
     * Retorna os atores e atrizes mais populares do momento.
     */
    @GetMapping("/popular-people")
    public List<MovieDTO> getPopularPeople() {
        return movieService.getPopularPeople();
    }

    /**
     * Busca multi-uso (filmes, séries). Suporta paginação.
     */
    @GetMapping("/search/{title}")
    public List<MovieDTO> getMovies(
            @PathVariable String title, 
            @RequestParam(defaultValue = "1") int page) {
        return movieService.searchMovies(title, page);
    }

    /**
     * Detalhes completos de uma obra.
     */
    @GetMapping("/details/{title}")
    public MovieDetailsDTO getMovieDetails(@PathVariable String title) {
        return movieService.getFullMovieDetails(title);
    }

    /**
     * Busca obras por nome de artista.
     */
    @GetMapping("/person/{name}")
    public List<MovieDTO> getMoviesByPerson(@PathVariable String name) {
        return movieService.searchByPerson(name);
    }

    /**
     * Busca obras por gênero com suporte a Infinite Scroll.
     */
    @GetMapping("/genre/{id}")
    public List<MovieDTO> getByGenre(
            @PathVariable Integer id, 
            @RequestParam(defaultValue = "1") int page) {
        return movieService.getMoviesByGenre(id, page);
    }
}