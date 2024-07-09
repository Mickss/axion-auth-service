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

    @Autowired
    private JwtService jwtService;

    public void createUser(CreateUserRequest createUserRequest) {
        log.info("Creating user {}", createUserRequest.getUsername());
        String hashedPassword = passwordService.hashPassword(createUserRequest.getPassword());
        userPersistApi.storeUser(createUserRequest.getUsername(), hashedPassword);
    }

    public String loginUser(LoginRequest loginRequest) throws UserLoginFailedException {
        List<User> users = userPersistApi.getUsers();
        String loginRequestUsername = loginRequest.getUsername();
        String loginRequestPassword = loginRequest.getPassword();
        Optional<User> optionalUser = users.stream()
                .filter(user -> user.getUsername().equals(loginRequestUsername))
                .findFirst();
        User user = optionalUser.orElseThrow(() -> new UserLoginFailedException("Incorrect login name " + loginRequestUsername));
        String encryptPassword = passwordService.hashPassword(loginRequestPassword);
        if (!user.getEncryptedPassword().equals(encryptPassword)) {
            throw new UserLoginFailedException("Login failed " + loginRequestUsername);
        }
        return jwtService.generateJwtToken(loginRequestUsername);
    }

    public void validateToken(ValidateTokenRequest validateTokenRequest) {
        jwtService.validateJwtToken(validateTokenRequest.getToken());
    }
}
