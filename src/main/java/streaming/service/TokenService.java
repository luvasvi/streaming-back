package streaming.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.expiration}")
    private Long expirationTime;

    private Key getSigningKey() {
        // .trim() garante que espaços invisíveis no properties não quebrem a chave
        byte[] keyBytes = secret.trim().getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String validarToken(String token) {
        try {
            if (token == null) return null;
            
            String tokenLimpo = token.replace("Bearer ", "").trim();
            
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(tokenLimpo)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            // Se o token estiver expirado ou a chave for diferente, retorna null
            return null; 
        }
    }
}