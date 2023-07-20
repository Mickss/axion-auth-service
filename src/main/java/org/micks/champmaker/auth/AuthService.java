package org.micks.champmaker.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserPersistApi userPersistApi;

    public void createUser(CreateUserRequest createUserRequest) {
        log.info("Creating user {}", createUserRequest.getUsername());
        String encryptedPassword = passwordService.encryptPassword(createUserRequest.getPassword());
        userPersistApi.storeUser(createUserRequest.getUsername(), encryptedPassword);
    }

    public void loginUser(LoginRequest loginRequest) {
        userPersistApi.readFile();
    }
}
