package org.micks.champmaker.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
@Slf4j
public class UserService {

    @Autowired DiscGolfDbConnection discGolfDbConnection;


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
}
