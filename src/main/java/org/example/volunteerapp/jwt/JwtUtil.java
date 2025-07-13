package org.example.volunteerapp.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private final String SECRET = "mysecretkey123";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 3;

    public String createToken(String email, String role) {
        return JWT.create()
                .withSubject(email)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET));
    }

    public DecodedJWT verifyToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(SECRET))
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            log.error("JWT 검증 실패: {}", e.getMessage());
            return null;
        }
    }

    public String getEmailFromToken(String token) {
        DecodedJWT decoded = verifyToken(token);
        return decoded != null ? decoded.getSubject() : null;
    }

    public String getRoleFromToken(String token) {
        DecodedJWT decoded = verifyToken(token);
        return decoded != null ? decoded.getClaim("role").asString() : null;
    }
}
