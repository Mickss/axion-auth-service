package org.micks.champmaker.auth;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    public static final int ONE_HOUR_IN_MILLIS = 3600000;

    public String generateJwtToken(String username) {
        long now = System.currentTimeMillis();
        SecretKey key = Jwts.SIG.HS256.key().build();

        return Jwts.builder().subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + ONE_HOUR_IN_MILLIS))
                .signWith(key).compact();
    }
}
