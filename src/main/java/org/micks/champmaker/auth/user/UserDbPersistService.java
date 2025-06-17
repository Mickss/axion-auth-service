package org.micks.champmaker.auth.user;

import lombok.extern.slf4j.Slf4j;
import org.micks.champmaker.auth.connection.DiscGolfDbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class UserDbPersistService implements UserPersistApi {

    @Autowired
    private DiscGolfDbConnection discGolfDbConnection;

    @Override
    public void storeUser(String username, String encryptedPassword) {
        log.info("Creating new user: {}", username);
        try (Connection connection = discGolfDbConnection.connect()) {
            PreparedStatement statement = connection.prepareStatement("insert into users values(UUID(),?,?,NULL, 'PLAYER')");
            statement.setString(1, username);
            statement.setString(2, encryptedPassword);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving user to database", e);
        }
    }

    @Override
    public List<User> getUsers() {
        return null;
    }
}
