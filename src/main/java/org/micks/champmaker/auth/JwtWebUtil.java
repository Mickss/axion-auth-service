package org.micks.champmaker.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class JwtWebUtil {

    public static String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header is missing or empty");
        }
        return authorizationHeader.substring("Bearer ".length());
    }
}
