package streaming.service;

import org.springframework.stereotype.Service;
import streaming.client.TmdbClient;
import streaming.dto.*;
import streaming.exception.MovieNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final TmdbClient tmdbClient;

    public MovieService(TmdbClient tmdbClient) {
        this.tmdbClient = tmdbClient;
    }

    public List<MovieDTO> getTrendingMovies(int page) {
        MovieResponseDTO response = tmdbClient.getTrending(page);
        if (response.getResults() == null) return List.of();

        return response.getResults().stream()
                .filter(item -> item.getPosterPath() != null)
                .collect(Collectors.toList());
    }

    public List<MovieDTO> getPopularPeople() {
        MovieResponseDTO response = tmdbClient.getPopularPeople();
        if (response.getResults() == null) return List.of();

        return response.getResults().stream()
                .filter(item -> item.getProfilePath() != null)
                .collect(Collectors.toList());
    }

    public List<MovieDTO> searchMovies(String title, int page) {
        MovieResponseDTO searchResult = tmdbClient.searchMulti(title, page);
        if (searchResult.getResults() == null || searchResult.getResults().isEmpty()) {
            throw new MovieNotFoundException("Nenhum resultado encontrado.");
        }
        
        return searchResult.getResults().stream()
            .filter(item -> {
                if ("person".equals(item.getMediaType())) {
                    return item.getProfilePath() != null;
                }
                return ("movie".equals(item.getMediaType()) || "tv".equals(item.getMediaType())) 
                        && item.getPosterPath() != null;
            })
            .collect(Collectors.toList());
    }

    public List<MovieDTO> searchByPerson(String name) {
        MovieResponseDTO personSearch = tmdbClient.searchPerson(name);
        if (personSearch.getResults() == null || personSearch.getResults().isEmpty()) {
            throw new MovieNotFoundException("Pessoa não encontrada: " + name);
        }

        Long personId = personSearch.getResults().get(0).getId();
        MovieResponseDTO allCredits = tmdbClient.getPersonCredits(personId);

        if (allCredits.getCast() == null) return List.of();

        return allCredits.getCast().stream()
            .filter(item -> item.getPosterPath() != null)
            .sorted(Comparator.comparing(MovieDTO::getPopularity, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
            .collect(Collectors.toList());
    }

    public MovieDetailsDTO getFullMovieDetails(String title) {
        MovieResponseDTO searchResult = tmdbClient.searchMulti(title, 1);
        if (searchResult.getResults() == null || searchResult.getResults().isEmpty()) {
            throw new MovieNotFoundException("Título não encontrado.");
        }

        MovieDTO basicMovie = searchResult.getResults().stream()
            .filter(item -> !"person".equals(item.getMediaType()))  
            .findFirst().orElse(searchResult.getResults().get(0));

        Long movieId = basicMovie.getId();
        String mediaType = basicMovie.getMediaType();
        
        String directorName = "Não informado";
        String tagline = null;
        Integer runtime = null;
        Long budget = 0L;
        Long revenue = 0L;

        // NOVAS VARIÁVEIS PARA SÉRIES
        Integer numberOfSeasons = null;
        List<SeasonDTO> seasons = null;

        CreditsResponseDTO credits;
        WatchProvidersResponseDTO providers;
        VideoResponseDTO videoResponse;
        MovieResponseDTO recommendationsResponse;

        if ("tv".equals(mediaType)) {
            credits = tmdbClient.getTvCredits(movieId);
            providers = tmdbClient.getTvWatchProviders(movieId);
            videoResponse = tmdbClient.getTvVideos(movieId);
            recommendationsResponse = tmdbClient.getTvRecommendations(movieId);
            
            MovieDTO tvDetails = tmdbClient.getTvDetails(movieId);
            if (tvDetails != null) {
                tagline = tvDetails.getTagline();
                
                // --- ATUALIZAÇÃO: CAPTURANDO DADOS DE TEMPORADA ---
                numberOfSeasons = tvDetails.getNumberOfSeasons();
                seasons = tvDetails.getSeasons();

                if (tvDetails.getCreatedBy() != null && !tvDetails.getCreatedBy().isEmpty()) {
                    directorName = tvDetails.getCreatedBy().get(0).getName();
                }
            }
        } else {
            credits = tmdbClient.getCredits(movieId);
            providers = tmdbClient.getWatchProviders(movieId);
            videoResponse = tmdbClient.getMovieVideos(movieId);
            recommendationsResponse = tmdbClient.getMovieRecommendations(movieId);
            
            MovieDTO movieDetails = tmdbClient.getMovieDetails(movieId);
            if (movieDetails != null) {
                tagline = movieDetails.getTagline();
                runtime = movieDetails.getRuntime();
                budget = movieDetails.getBudget();
                revenue = movieDetails.getRevenue();
            }
            
            if (credits != null && credits.getCrew() != null) {
                for (CrewMemberDTO crew : credits.getCrew()) {
                    if ("Director".equals(crew.getJob())) {
                        directorName = crew.getName();
                        break;
                    }
                }
            }
        }

        String trailerKey = extrairTrailerKey(videoResponse);
        if (trailerKey == null) {
            VideoResponseDTO videoResponseEn = "tv".equals(mediaType) ? 
                tmdbClient.getTvVideosEn(movieId) : tmdbClient.getMovieVideosEn(movieId);
            trailerKey = extrairTrailerKey(videoResponseEn);
        }

        List<MovieDTO> recommendations = List.of();
        if (recommendationsResponse != null && recommendationsResponse.getResults() != null) {
            recommendations = recommendationsResponse.getResults().stream()
                .filter(item -> item.getPosterPath() != null)
                .limit(10)
                .collect(Collectors.toList());
        }

        MovieDetailsDTO details = new MovieDetailsDTO();
        details.setId(basicMovie.getId());
        details.setTitle(basicMovie.getDisplayName());
        details.setOverview(basicMovie.getOverview());
        details.setPosterPath(basicMovie.getPosterPath());
        details.setVoteAverage(basicMovie.getVoteAverage());
        details.setReleaseDate(basicMovie.getReleaseDate());
        details.setFirstAirDate(basicMovie.getFirstAirDate());
        details.setDirector(directorName);
        details.setCast(credits != null ? credits.getCast() : List.of());
        details.setWatchProviders(providers != null && providers.getResults() != null ? providers.getResults().get("BR") : null);
        
        details.setTagline(tagline);
        details.setRuntime(runtime);
        details.setBudget(budget);
        details.setRevenue(revenue);
        details.setTrailerKey(trailerKey);
        details.setRecommendations(recommendations);

        // --- ATUALIZAÇÃO: ENVIANDO PARA O FRONT ---
        details.setNumberOfSeasons(numberOfSeasons);
        details.setSeasons(seasons);

        return details;
    }

    private String extrairTrailerKey(VideoResponseDTO response) {
        if (response != null && response.getResults() != null) {
            return response.getResults().stream()
                .filter(v -> "YouTube".equals(v.getSite()) && "Trailer".equals(v.getType()))
                .map(VideoDTO::getKey)
                .findFirst()
                .orElse(null);
        }
        return null;
    }

    public List<MovieDTO> getMoviesByGenre(Integer genreId, int page, String sortBy) {
        MovieResponseDTO response = tmdbClient.getMoviesByGenre(genreId, page, sortBy);
        if (response.getResults() == null) return List.of();

        return response.getResults().stream()
                .filter(item -> item.getPosterPath() != null)
                .collect(Collectors.toList());
    }

    public SeasonDetailsDTO getSeasonEpisodes(Long seriesId, Integer seasonNumber) {
    return tmdbClient.getSeasonDetails(seriesId, seasonNumber);
}
}