package streaming.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Base64;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        try {
            String username = loginData.get("username");
            String password = loginData.get("password");

            // Valida as credenciais contra os 5 usuários cadastrados
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            // Se deu certo, gera o token "Basic"
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

            Map<String, String> response = new HashMap<>();
            response.put("token", "Basic " + encodedAuth);
            response.put("username", username);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            // Se a senha estiver errada, retorna 401 para o Angular mostrar o alerta
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }
}