package streaming.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class CreditsResponseDTO {
    private List<CastMemberDTO> cast;
    private List<CrewMemberDTO> crew;
    @JsonProperty("created_by")
    private List<CrewMemberDTO> createdBy; 
}