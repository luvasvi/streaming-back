package streaming.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import streaming.dto.*;

@Component
public class TmdbClient {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // ... (Mantenha todos os seus métodos de busca de Home, Busca, Créditos e Trailers iguais)

    public MovieResponseDTO getTrending(int page) {
        String url = apiUrl + "/trending/all/day?api_key=" + apiKey + "&language=pt-BR&page=" + page;
        return restTemplate.getForObject(url, MovieResponseDTO.class);
    }

    public MovieResponseDTO getPopularPeople() {
        String url = apiUrl + "/person/popular?api_key=" + apiKey + "&language=pt-BR";
        return restTemplate.getForObject(url, MovieResponseDTO.class);
    }

    public MovieResponseDTO searchMulti(String query, int page) {
        String url = apiUrl + "/search/multi?api_key=" + apiKey + "&query=" + query + "&language=pt-BR&page=" + page;
        return restTemplate.getForObject(url, MovieResponseDTO.class);
    }

    public MovieResponseDTO getMoviesByGenre(Integer genreId, int page, String sortBy) {
        String url = apiUrl + "/discover/movie?api_key=" + apiKey + "&with_genres=" + genreId + 
                     "&language=pt-BR&sort_by=" + sortBy + "&page=" + page;
        return restTemplate.getForObject(url, MovieResponseDTO.class);
    }

    public MovieResponseDTO searchPerson(String name) {
        String url = apiUrl + "/search/person?api_key=" + apiKey + "&query=" + name + "&language=pt-BR";
        return restTemplate.getForObject(url, MovieResponseDTO.class);
    }

    public MovieResponseDTO getPersonCredits(Long personId) {
        String url = apiUrl + "/person/" + personId + "/combined_credits?api_key=" + apiKey + "&language=pt-BR";
        return restTemplate.getForObject(url, MovieResponseDTO.class);
    }

    public CreditsResponseDTO getCredits(Long movieId) {
        String url = apiUrl + "/movie/" + movieId + "/credits?api_key=" + apiKey + "&language=pt-BR";
        return restTemplate.getForObject(url, CreditsResponseDTO.class);
    }

    public WatchProvidersResponseDTO getWatchProviders(Long movieId) {
        String url = apiUrl + "/movie/" + movieId + "/watch/providers?api_key=" + apiKey;
        return restTemplate.getForObject(url, WatchProvidersResponseDTO.class);
    }

    public CreditsResponseDTO getTvCredits(Long seriesId) {
        String url = apiUrl + "/tv/" + seriesId + "/credits?api_key=" + apiKey + "&language=pt-BR";
        return restTemplate.getForObject(url, CreditsResponseDTO.class);
    }

    public WatchProvidersResponseDTO getTvWatchProviders(Long seriesId) {
        String url = apiUrl + "/tv/" + seriesId + "/watch/providers?api_key=" + apiKey;
        return restTemplate.getForObject(url, WatchProvidersResponseDTO.class);
    }

    public MovieDTO getTvDetails(Long seriesId) {
        String url = apiUrl + "/tv/" + seriesId + "?api_key=" + apiKey + "&language=pt-BR";
        return restTemplate.getForObject(url, MovieDTO.class);
    }

    public VideoResponseDTO getMovieVideos(Long movieId) {
        String url = apiUrl + "/movie/" + movieId + "/videos?api_key=" + apiKey + "&language=pt-BR";
        return restTemplate.getForObject(url, VideoResponseDTO.class);
    }

    public VideoResponseDTO getTvVideos(Long seriesId) {
        String url = apiUrl + "/tv/" + seriesId + "/videos?api_key=" + apiKey + "&language=pt-BR";
        return restTemplate.getForObject(url, VideoResponseDTO.class);
    }

    public MovieResponseDTO getMovieRecommendations(Long movieId) {
        String url = apiUrl + "/movie/" + movieId + "/recommendations?api_key=" + apiKey + "&language=pt-BR";
        return restTemplate.getForObject(url, MovieResponseDTO.class);
    }

    public MovieResponseDTO getTvRecommendations(Long seriesId) {
        String url = apiUrl + "/tv/" + seriesId + "/recommendations?api_key=" + apiKey + "&language=pt-BR";
        return restTemplate.getForObject(url, MovieResponseDTO.class);
    }

    public VideoResponseDTO getMovieVideosEn(Long movieId) {
        String url = apiUrl + "/movie/" + movieId + "/videos?api_key=" + apiKey + "&language=en-US";
        return restTemplate.getForObject(url, VideoResponseDTO.class);
    }

    public VideoResponseDTO getTvVideosEn(Long seriesId) {
        String url = apiUrl + "/tv/" + seriesId + "/videos?api_key=" + apiKey + "&language=en-US";
        return restTemplate.getForObject(url, VideoResponseDTO.class);
    }

    public MovieDTO getMovieDetails(Long movieId) {
        String url = apiUrl + "/movie/" + movieId + "?api_key=" + apiKey + "&language=pt-BR";
        return restTemplate.getForObject(url, MovieDTO.class);
    }

    // --- NOVO: BUSCA DETALHES DA TEMPORADA (EPISÓDIOS) ---
    public SeasonDetailsDTO getSeasonDetails(Long seriesId, Integer seasonNumber) {
        String url = apiUrl + "/tv/" + seriesId + "/season/" + seasonNumber + "?api_key=" + apiKey + "&language=pt-BR";
        return restTemplate.getForObject(url, SeasonDetailsDTO.class);
    }
}