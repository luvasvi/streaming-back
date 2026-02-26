package streaming.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProviderDTO {
    
    @JsonProperty("provider_name")
    private String providerName; // Nome da plataforma (ex: "Netflix")
    
    @JsonProperty("logo_path")
    private String logoPath; // Caminho para a imagem do logótipo
}