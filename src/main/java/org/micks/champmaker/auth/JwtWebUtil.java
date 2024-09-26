package org.micks.champmaker.auth;

public class JwtWebUtil {

    public static String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            throw new IllegalStateException("Authorization header is missing or empty: " + authorizationHeader);
        }
        return authorizationHeader.substring("Bearer ".length());
    }
}
