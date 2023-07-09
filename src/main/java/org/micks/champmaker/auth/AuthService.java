package org.micks.champmaker.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    public void createUser(CreateUserRequest createUserRequest) {
        String username = createUserRequest.getUsername();
        log.info("Creating user {}", username);
    }
}
