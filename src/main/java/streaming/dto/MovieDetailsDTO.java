package streaming.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class MovieDetailsDTO {
    private Long id;
    private String title;
    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("vote_average")
    private Double voteAverage;
    
    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("first_air_date")
    private String firstAirDate;

    private String director;
    private List<CastMemberDTO> cast;

    @JsonProperty("watch_providers")
    private CountryProviderDTO watchProviders;

    // --- NOVOS CAMPOS PARA DADOS EXTRAS ---
    private Integer runtime;      // Duração em minutos
    private Long budget;          // Orçamento
    private Long revenue;         // Faturamento
    private String tagline;       // Frase de efeito

    private String trailerKey;    // ID do vídeo do YouTube
    private List<MovieDTO> recommendations; // Filmes semelhantes

    // --- NOVOS CAMPOS PARA SÉRIES (TEMPORADAS) ---
    @JsonProperty("number_of_seasons")
    private Integer numberOfSeasons;

    private List<SeasonDTO> seasons;
}