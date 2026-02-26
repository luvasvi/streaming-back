package streaming.dto;
import java.util.List;
import lombok.Data;

@Data
public class VideoResponseDTO {
    private List<VideoDTO> results;
}