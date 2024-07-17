package org.micks.champmaker.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    @Qualifier("userDbPersistService")
    private UserPersistApi userPersistApi;

    @Autowired
    private DiscGolfDbConnection discGolfDbConnection;

    @Autowired
    private JwtService jwtService;

    public void createUser(CreateUserRequest createUserRequest) {
        log.info("Creating user {}", createUserRequest.getUsername());
        String hashedPassword = passwordService.hashPassword(createUserRequest.getPassword());
        userPersistApi.storeUser(createUserRequest.getUsername(), hashedPassword);
    }

    public String loginUser(LoginRequest loginRequest) throws UserLoginFailedException {
        String loginRequestUsername = loginRequest.getUsername();
        String loginRequestPassword = loginRequest.getPassword();
        try (Connection connection = discGolfDbConnection.connect()) {
            PreparedStatement statement = connection.prepareStatement("SELECT password_hash FROM users WHERE username = ?");
            statement.setString(1, loginRequest.getUsername());
            ResultSet resultSet = statement.executeQuery();
            String hash = resultSet.getString("password_hash");
            String encryptPassword = passwordService.hashPassword(loginRequestPassword);
            if (!hash.equals(encryptPassword)) {
                throw new UserLoginFailedException("Login failed " + loginRequestUsername);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return jwtService.generateJwtToken(loginRequestUsername);
    }

    public void validateToken(ValidateTokenRequest validateTokenRequest) {
        jwtService.validateJwtToken(validateTokenRequest.getToken());
    }
}
