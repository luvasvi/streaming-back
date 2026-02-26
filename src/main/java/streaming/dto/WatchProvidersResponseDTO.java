package streaming.dto;

import java.util.Map;
import lombok.Data;

@Data
public class WatchProvidersResponseDTO {
    
    // O Map associa a sigla do país aos dados de streaming dele
    private Map<String, CountryProviderDTO> results;
}