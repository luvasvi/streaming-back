package streaming.exception;

// Estender RuntimeException significa que este é um erro que ocorre com o programa rodando
public class MovieNotFoundException extends RuntimeException {
    
    public MovieNotFoundException(String message) {
        super(message);
    }
}