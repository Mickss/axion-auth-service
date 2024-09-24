package org.micks.champmaker.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PutMapping(value = "/{userId}/promote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void promoteToAdmin(@PathVariable String userId) {
        //TODO check if logged in user is admin
        userService.promoteToAdmin(userId);
    }

    @PutMapping(value = "/{userId}/downgrade", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void downgradeToPlayer(@PathVariable String userId) {
        //TODO check if logged in user is player
        userService.downgradeToPlayer(userId);
    }

    @GetMapping(value = "/logged-in", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRecord getLoggedInUser(@RequestHeader(value = "Authorization") String authorizationHeader) {
        String jwtToken = JwtWebUtil.extractTokenFromHeader(authorizationHeader);
        String userId = jwtService.getSubject(jwtToken);
        return userService.getUser(userId);
    }
}
