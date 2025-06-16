package org.micks.champmaker.auth.user;

import lombok.extern.slf4j.Slf4j;
import org.micks.champmaker.auth.jwt.JwtService;
import org.micks.champmaker.auth.jwt.JwtWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PutMapping(value = "/{userId}/promote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void promoteToAdmin(@RequestHeader("X-User-Role") String userRole, @PathVariable String userId) {
        if (!"ADMIN".equals(userRole)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only ADMINS can promote users");
        }
        log.info("Promoting user {} to ADMIN", userId);
        userService.promoteToAdmin(userId);
    }

    @PutMapping(value = "/{userId}/downgrade", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void downgradeToPlayer(@RequestHeader("X-User-Role") String userRole, @PathVariable String userId) {
        if (!"ADMIN".equals(userRole)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only ADMINS can downgrade users");
        }
        log.info("Downgrading user {} to PLAYER", userId);
        userService.downgradeToPlayer(userId);
    }

    @GetMapping(value = "/logged-in", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRecord getLoggedInUser(@RequestHeader(value = "Authorization") String authorizationHeader) {
        String jwtToken = JwtWebUtil.extractTokenFromHeader(authorizationHeader);
        log.info("Extracted JWT Token: {}", jwtToken);
        String userId = jwtService.getSubject(jwtToken);
        return userService.getUser(userId);
    }
}
