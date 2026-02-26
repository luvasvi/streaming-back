package streaming.dto;

import java.util.List;
import lombok.Data;

@Data
public class MovieResponseDTO {

    private List<MovieDTO> results;
    private List<MovieDTO> cast;
}