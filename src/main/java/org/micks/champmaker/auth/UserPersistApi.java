package org.micks.champmaker.auth;

public interface UserPersistApi {

    void storeUser(String username, String encryptedPassword);
}
