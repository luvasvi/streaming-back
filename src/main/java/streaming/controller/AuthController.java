package streaming.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import streaming.service.TokenService; 
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService; 

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        try {
            String username = loginData.get("username"); 
            String password = loginData.get("password");

            
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            
            String token = tokenService.gerarToken(username);

            Map<String, String> response = new HashMap<>();
            response.put("token", "Bearer " + token); 
            response.put("username", username);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            
            return ResponseEntity.status(401).body(Map.of("erro", "Credenciais inválidas"));
        }
    }
}