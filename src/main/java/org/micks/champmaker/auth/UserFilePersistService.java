package org.micks.champmaker.auth;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

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
}
