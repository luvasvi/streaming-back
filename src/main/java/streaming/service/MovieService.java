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


    public MovieDetailsDTO getFullMovieDetailsById(String mediaType, String idStr) {
        Long movieId = Long.parseLong(idStr);
        
        String directorName = "Não informado";
        String tagline = null;
        Integer runtime = null;
        Long budget = 0L;
        Long revenue = 0L;
        Integer numberOfSeasons = null;
        List<SeasonDTO> seasons = null;

        CreditsResponseDTO credits;
        WatchProvidersResponseDTO providers;
        VideoResponseDTO videoResponse;
        MovieResponseDTO recommendationsResponse;
        MovieDTO basicDetails;

        // Busca os detalhes baseados no tipo enviado pelo Angular
        if ("tv".equals(mediaType)) {
            basicDetails = tmdbClient.getTvDetails(movieId);
            credits = tmdbClient.getTvCredits(movieId);
            providers = tmdbClient.getTvWatchProviders(movieId);
            videoResponse = tmdbClient.getTvVideos(movieId);
            recommendationsResponse = tmdbClient.getTvRecommendations(movieId);
            
            if (basicDetails != null) {
                tagline = basicDetails.getTagline();
                numberOfSeasons = basicDetails.getNumberOfSeasons();
                seasons = basicDetails.getSeasons();
                if (basicDetails.getCreatedBy() != null && !basicDetails.getCreatedBy().isEmpty()) {
                    directorName = basicDetails.getCreatedBy().get(0).getName();
                }
            }
        } else {
            basicDetails = tmdbClient.getMovieDetails(movieId);
            credits = tmdbClient.getCredits(movieId);
            providers = tmdbClient.getWatchProviders(movieId);
            videoResponse = tmdbClient.getMovieVideos(movieId);
            recommendationsResponse = tmdbClient.getMovieRecommendations(movieId);
            
            if (basicDetails != null) {
                tagline = basicDetails.getTagline();
                runtime = basicDetails.getRuntime();
                budget = basicDetails.getBudget();
                revenue = basicDetails.getRevenue();
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

        if (basicDetails == null) throw new MovieNotFoundException("Obra não encontrada.");

        // Lógica de Trailer (PT-BR ou EN)
        String trailerKey = extrairTrailerKey(videoResponse);
        if (trailerKey == null) {
            VideoResponseDTO videoResponseEn = "tv".equals(mediaType) ? 
                tmdbClient.getTvVideosEn(movieId) : tmdbClient.getMovieVideosEn(movieId);
            trailerKey = extrairTrailerKey(videoResponseEn);
        }

        // Recomendações
        List<MovieDTO> recommendations = (recommendationsResponse != null && recommendationsResponse.getResults() != null) ?
            recommendationsResponse.getResults().stream()
                .filter(item -> item.getPosterPath() != null)
                .limit(10).collect(Collectors.toList()) : List.of();

        // Montagem do DTO de retorno
        MovieDetailsDTO details = new MovieDetailsDTO();
        details.setId(basicDetails.getId());
        details.setTitle(basicDetails.getDisplayName());
        details.setOverview(basicDetails.getOverview());
        details.setPosterPath(basicDetails.getPosterPath());
        details.setVoteAverage(basicDetails.getVoteAverage());
        details.setReleaseDate(basicDetails.getReleaseDate());
        details.setFirstAirDate(basicDetails.getFirstAirDate());
        details.setDirector(directorName);
        details.setCast(credits != null ? credits.getCast() : List.of());
        details.setWatchProviders(providers != null && providers.getResults() != null ? providers.getResults().get("BR") : null);
        details.setTagline(tagline);
        details.setRuntime(runtime);
        details.setBudget(budget);
        details.setRevenue(revenue);
        details.setTrailerKey(trailerKey);
        details.setRecommendations(recommendations);
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