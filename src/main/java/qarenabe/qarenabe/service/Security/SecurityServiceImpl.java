package qarenabe.qarenabe.service.Security;

import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SecurityServiceImpl implements SecurityService {
    private static final String SECRET_KEY = "3fSx8yA5T/2yqO6JGhF9UzD2hVtXz9F+X3l5Q9bF3Kc=";

    @Override
    public String encode(String text) {
        // Encode plain text to Base64
        String encodedString = Base64.getEncoder().encodeToString(text.getBytes());
        return encodedString;
    }

    @Override
    public String decode(String text) {
        // Decode Base64 to plain text
        byte[] decodedBytes = Base64.getDecoder().decode(text);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
    @Override
    public Boolean verifyToken(String token, String subject) {
        @SuppressWarnings("deprecation")
        Claims data =  Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        boolean checkBool = data.getSubject().equals(subject);
        checkBool = data.getExpiration().after(new Date(data.getExpiration().getTime() + 86400000));
        return checkBool;
    }

    @Override
    public String generateToken(String subject) {
        @SuppressWarnings("deprecation")
        String jwtToken = Jwts.builder()
                            .setSubject(subject)
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day validity
                            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                            .compact();
        return jwtToken;
    }
}
