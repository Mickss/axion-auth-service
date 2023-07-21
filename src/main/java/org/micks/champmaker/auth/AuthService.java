package org.micks.champmaker.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        List<User> users = userPersistApi.readFile();
        String loginRequestUsername = loginRequest.getUsername();
        String loginRequestPassword = loginRequest.getPassword();
        Optional<User> first = users.stream()
                .filter(user -> user.getUsername().equals(loginRequestUsername))
                .findFirst();
        User foundUser = first.orElseThrow();
        String encryptPassword = passwordService.encryptPassword(loginRequestPassword);
        if (!foundUser.getEncryptedPassword().equals(encryptPassword)) {
            throw new RuntimeException("Login failed");
        }
    }
}
