package org.micks.champmaker.auth.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/public/users")
public class UserPublicController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<UserRecord> getUsers() {
        return userService.getUsers();
    }
}
