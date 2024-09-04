package org.micks.champmaker.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @Autowired UserService userService;

    @PutMapping(value = "/{userId}/promote", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void promoteToAdmin(@PathVariable String userId) {
        //TODO check if logged in user is admin
        userService.promoteToAdmin(userId);
    }
    @PutMapping(value = "/{userId}/K", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void downgradeToPlayer(@PathVariable String userId) {
        //TODO check if logged in user is player
        userService.downgradeToPlayer(userId);
    }
}
