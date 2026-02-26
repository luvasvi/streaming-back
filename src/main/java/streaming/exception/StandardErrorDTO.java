package streaming.exception;

import lombok.Data;
import java.time.Instant;

@Data
public class StandardErrorDTO {
    private Instant timestamp; // A data e hora exata do erro
    private Integer status;    // O código HTTP (ex: 404 para Não Encontrado)
    private String error;      // O tipo do erro
    private String message;    // A mensagem amigável para o usuário
    private String path;       // A URL que tentaram acessar
}