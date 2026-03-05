package streaming.controller;

import org.springframework.web.bind.annotation.*;
import streaming.dto.MovieDTO;
import streaming.dto.MovieDetailsDTO;
import streaming.dto.SeasonDetailsDTO;
import streaming.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "*") 
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/trending")
    public List<MovieDTO> getTrending(@RequestParam(defaultValue = "1") int page) {
        return movieService.getTrendingMovies(page);
    }

    @GetMapping("/popular-people")
    public List<MovieDTO> getPopularPeople() {
        return movieService.getPopularPeople();
    }

    @GetMapping("/search/{title}")
    public List<MovieDTO> getMovies(
            @PathVariable String title, 
            @RequestParam(defaultValue = "1") int page) {
        return movieService.searchMovies(title, page);
    }

    /**
     * ATUALIZADO: Agora recebe o TIPO (movie/tv) e o ID direto do Angular.
     * Isso resolve o erro de trocar o anime de One Piece pela série.
     */
    @GetMapping("/details/{type}/{id}")
    public MovieDetailsDTO getMovieDetails(
            @PathVariable String type, 
            @PathVariable String id) {
        // Agora o service deve buscar exatamente por ID e Tipo
        return movieService.getFullMovieDetailsById(type, id);
    }

    @GetMapping("/person/{name}")
    public List<MovieDTO> getMoviesByPerson(@PathVariable String name) {
        return movieService.searchByPerson(name);
    }

    @GetMapping("/genre/{id}")
    public List<MovieDTO> getByGenre(
            @PathVariable Integer id, 
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "popularity.desc") String sortBy) { 
        return movieService.getMoviesByGenre(id, page, sortBy);
    }
    
    @GetMapping("/series/{id}/season/{number}")
    public SeasonDetailsDTO getSeasonDetails(@PathVariable Long id, @PathVariable Integer number) {
        return movieService.getSeasonEpisodes(id, number);
    }
}