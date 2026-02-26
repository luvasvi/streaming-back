package streaming.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

// @ControllerAdvice avisa ao Spring que esta classe vai interceptar erros de todos os Controllers
@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<StandardErrorDTO> movieNotFound(MovieNotFoundException e, HttpServletRequest request) {
        
        StandardErrorDTO error = new StandardErrorDTO();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value()); // Retorna o código 404
        error.setError("Recurso não encontrado");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}