package org.micks.champmaker.auth;

import java.util.List;

public interface UserPersistApi {

    void storeUser(String username, String encryptedPassword);
    List<User> readFile();
}
