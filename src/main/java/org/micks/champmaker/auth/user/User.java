package org.micks.champmaker.auth.user;

@Deprecated
public class User {

    private String email;
    private String encryptedPassword;

    public User(String email, String encryptedPassword) {
        this.email = email;
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }
}
