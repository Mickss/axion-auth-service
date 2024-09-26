package org.micks.champmaker.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Service
public class JwtService {

    private static final int ONE_HOUR_IN_MILLIS = 3_600_000;
    private static final String SECRET = "a7RGqLpiXLeCjqdGPwdFShrXM5QExGgD"; //TODO Extract secret from some more safer place

    public String generateJwtToken(String userId) {
        long now = System.currentTimeMillis();

        return Jwts.builder().subject(userId)
                .issuedAt(new Date(now))
                .expiration(new Date(now + ONE_HOUR_IN_MILLIS))
                .signWith(getKey()).compact();
    }

    public Jws<Claims> validateJwtToken(String jwtToken) {
        SecretKey key = getKey();
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken);
    }

    private SecretKey getKey() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        return new SecretKeySpec(SECRET.getBytes(), key.getAlgorithm());
    }

    public String getSubject(String jwtToken) {
        Jws<Claims> claims = validateJwtToken(jwtToken);
        return claims.getPayload().getSubject();
    }
}
