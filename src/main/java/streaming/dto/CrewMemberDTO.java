package streaming.dto;

import lombok.Data;

@Data
public class CrewMemberDTO {
    
    private String name; // Nome do profissional
    private String job; // Cargo (ex: "Director", "Producer", "Original Music Composer")
}