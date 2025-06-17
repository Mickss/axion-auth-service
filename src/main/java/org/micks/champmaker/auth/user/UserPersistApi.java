package org.micks.champmaker.auth.user;

import java.util.List;

public interface UserPersistApi {

    void storeUser(String username, String encryptedPassword);
    List<User> getUsers();
}
