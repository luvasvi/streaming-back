package streaming.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class SeasonDetailsDTO {
    private String _id;
    private String name;
    private String overview;
    private Integer id;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("season_number")
    private Integer seasonNumber;

    private List<EpisodeDTO> episodes;
}