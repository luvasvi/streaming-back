package streaming.dto;

import java.util.List;
import lombok.Data;

@Data
public class CountryProviderDTO {
    
    private String link; // Link oficial do JustWatch para a página do filme
    
    // Plataformas de Streaming (Assinatura)
    private List<ProviderDTO> flatrate; 
    
    // Plataformas para Aluguer
    private List<ProviderDTO> rent;     
    
    // Plataformas para Compra
    private List<ProviderDTO> buy;      
}