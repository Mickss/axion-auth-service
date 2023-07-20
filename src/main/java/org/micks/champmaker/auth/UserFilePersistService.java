package org.micks.champmaker.auth;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> readFile() {
        try {
            FileReader fileRead = new FileReader("C:\\Workspace\\champmaker-auth\\registered-users.txt");
            BufferedReader inStream = new BufferedReader(fileRead);

            List<String> users = new ArrayList<>();
            String inString;

            while ((inString = inStream.readLine()) != null) {
                users.add(inString);
                System.out.println("Users " + inString);
            }
            return users;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
