package org.micks.champmaker.auth.user;

import lombok.extern.slf4j.Slf4j;
import org.micks.champmaker.auth.connection.DiscGolfDbConnection;
import org.micks.champmaker.auth.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@Slf4j
public class UserService {

    @Autowired
    DiscGolfDbConnection discGolfDbConnection;


    public void promoteToAdmin(String userId) {
        log.info("Promoting user {} to ADMIN", userId);
        try (Connection connection = discGolfDbConnection.connect()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET role = 'ADMIN' WHERE user_id = ?");
            statement.setString(1, userId);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error while promoting user to ADMIN", e);
        }
    }

    public void downgradeToPlayer(String userId) {
        log.info("Downgrade user {} to PLAYER", userId);
        try (Connection connection = discGolfDbConnection.connect()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET role = 'PLAYER' WHERE user_id = ?");
            statement.setString(1, userId);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error while downgrading user to PLAYER", e);
        }
    }

    public UserRecord getUser(String userId) {
        log.info("Getting user by Id: {}", userId);
        try (Connection connection = discGolfDbConnection.connect()) {
            PreparedStatement statement = connection.prepareStatement("SELECT user_id, username, role FROM users WHERE user_id = ?");
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String dbUserId = resultSet.getString("user_id");
                String username = resultSet.getString("username");
                String roleString = resultSet.getString("role");
                UserRole role = UserRole.valueOf(roleString.toUpperCase());
                return new UserRecord(dbUserId, username, role);
            } else {
                throw new UserNotFoundException("User with ID " + userId + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting user by ID", e);
        }
    }

    public boolean isUserAdmin(String userId) {
        UserRecord user = getUser(userId);
        return user.role() == UserRole.ADMIN;
    }
}
