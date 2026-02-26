package streaming.dto;
import lombok.Data;

@Data
public class VideoDTO {
    private String name; // Ex: "Trailer Oficial"
    private String key;  // A chave do YouTube (ex: dQw4w9WgXcQ)
    private String site; // Ex: "YouTube"
    private String type; // Ex: "Trailer", "Teaser"
}