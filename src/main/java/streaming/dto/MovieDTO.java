package streaming.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class MovieDTO {
    
    private Long id;
    private String title;
    
    @JsonProperty("name") 
    private String name;
    
    private String overview;
    
    @JsonProperty("poster_path")
    private String posterPath;
    
    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("first_air_date")
    private String firstAirDate;

    @JsonProperty("profile_path")
    private String profilePath;
    
    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("popularity")
    private Double popularity;

    @JsonProperty("media_type")
    private String mediaType;

    @JsonProperty("known_for")
    private List<MovieDTO> knownFor;

    // --- NOVOS CAMPOS PARA DADOS EXTRAS ---
    private Integer runtime;      // Duração em minutos
    private Long budget;          // Orçamento
    private Long revenue;         // Faturamento
    private String tagline;       // Frase de efeito

    // NOVO: Adicionado para capturar quem criou a série (Diretor/Criador em TV)
    @JsonProperty("created_by")
    private List<CastMemberDTO> createdBy;

    // --- NOVOS CAMPOS PARA SÉRIES (O QUE FALTAVA) ---
    @JsonProperty("number_of_seasons")
    private Integer numberOfSeasons;

    private List<SeasonDTO> seasons;

    public String getDisplayName() {
        return (title != null && !title.isEmpty()) ? title : name;
    }
}