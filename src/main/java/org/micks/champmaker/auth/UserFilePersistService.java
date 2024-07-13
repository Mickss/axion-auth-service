package org.micks.champmaker.auth;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Deprecated // User data will be saved in database instead of file
@Service
public class UserFilePersistService implements UserPersistApi {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    @Override
    public void storeUser(String username, String encryptedPassword) {
        try {
            FileWriter fileWriter = new FileWriter("registered-users.txt", true);
            fileWriter.write(username + "@@@@" + encryptedPassword + LINE_SEPARATOR);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private User convertLineToUser(String userLine) {
        String[] split = userLine.split("@@@@", 2);
        return new User(split[0], split[1]);
    }

    @Override
    public List<User> getUsers() {
        try {
            FileReader fileReader = new FileReader("registered-users.txt");
            BufferedReader usersReader = new BufferedReader(fileReader);
            List<User> users = new ArrayList<>();
            String userLine;
            while ((userLine = usersReader.readLine()) != null) {
                users.add(convertLineToUser(userLine));
            }
            return users;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
