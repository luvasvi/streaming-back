package streaming.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CastMemberDTO {
    
    private String name; // Nome do ator na vida real
    private String character; // Nome do personagem no filme
    
    @JsonProperty("profile_path")
    private String profilePath; // Caminho para a foto do ator
}