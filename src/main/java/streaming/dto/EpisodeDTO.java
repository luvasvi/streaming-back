package streaming.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EpisodeDTO {
    private Long id;
    private String name;
    private String overview;

    @JsonProperty("episode_number")
    private Integer episodeNumber;

    @JsonProperty("still_path")
    private String stillPath; // Foto do episódio

    @JsonProperty("vote_average")
    private Double voteAverage;
}