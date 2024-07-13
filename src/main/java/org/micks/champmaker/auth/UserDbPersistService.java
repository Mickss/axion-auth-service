package org.micks.champmaker.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserDbPersistService implements UserPersistApi {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private DiscGolfDbConnection discGolfDbConnection;

    @Override
    public void storeUser(String username, String encryptedPassword) {
        log.info("Creating new user: {}", username, encryptedPassword);
        try (Connection connection = discGolfDbConnection.connect()) {
            PreparedStatement statement = connection.prepareStatement("insert into users values(UUID(),?,?,?)");
            statement.setString(1, username);
            statement.setString(2, encryptedPassword);
            statement.setString(3, DATE_FORMAT.format(new Date()));
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getUsers() {
        return null;
    }
}
