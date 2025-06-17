package org.micks.champmaker.auth.user;

public class CreateUserRequest {

    private String email;
    private String password;

    public CreateUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
