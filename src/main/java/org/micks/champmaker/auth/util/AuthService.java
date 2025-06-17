package org.micks.champmaker.auth.util;

import lombok.extern.slf4j.Slf4j;
import org.micks.champmaker.auth.connection.DiscGolfDbConnection;
import org.micks.champmaker.auth.connection.PasswordService;
import org.micks.champmaker.auth.exceptions.UserAlreadyExistsException;
import org.micks.champmaker.auth.exceptions.UserLoginFailedException;
import org.micks.champmaker.auth.jwt.JwtService;
import org.micks.champmaker.auth.jwt.ValidateTokenRequest;
import org.micks.champmaker.auth.user.CreateUserRequest;
import org.micks.champmaker.auth.user.LoginRequest;
import org.micks.champmaker.auth.user.UserPersistApi;
import org.mindrot.jbcrypt.BCrypt;
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

        try (Connection connection = discGolfDbConnection.connect()) {
            PreparedStatement checkStatement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?");
            checkStatement.setString(1, createUserRequest.getUsername());
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                throw new UserAlreadyExistsException("Username already exist: " + createUserRequest.getUsername());
            }
            String hashedPassword = passwordService.hashPassword(createUserRequest.getPassword());
            userPersistApi.storeUser(createUserRequest.getUsername(), hashedPassword);
        } catch (SQLException e) {
            throw new RuntimeException("Database error during user creation", e);
        }
    }

    public String loginUser(LoginRequest loginRequest) throws UserLoginFailedException {
        String loginRequestUsername = loginRequest.getUsername();
        String loginRequestPassword = loginRequest.getPassword();
        try (Connection connection = discGolfDbConnection.connect()) {
            PreparedStatement statement = connection.prepareStatement("SELECT user_id, password_hash FROM users WHERE username = ?");
            statement.setString(1, loginRequest.getUsername());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String hash = resultSet.getString("password_hash");
                if (!BCrypt.checkpw(loginRequestPassword, hash)) {
                    throw new UserLoginFailedException("Incorrect password " + loginRequestUsername);
                }
                return jwtService.generateJwtToken(userId);
            } else {
                throw new UserLoginFailedException("Incorrect login " + loginRequestUsername);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void validateToken(ValidateTokenRequest validateTokenRequest) {
        jwtService.validateJwtToken(validateTokenRequest.getToken());
    }
}
