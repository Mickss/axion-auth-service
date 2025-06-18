package org.micks.champmaker.auth.user;

import java.util.List;

public interface UserPersistApi {

    void storeUser(String email, String encryptedPassword);
    List<User> getUsers();
}
