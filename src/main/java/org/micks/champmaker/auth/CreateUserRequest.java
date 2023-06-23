package org.micks.champmaker.auth;


import lombok.NonNull;

public class CreateUserRequest {

    private Long id;
    @NonNull
    private String user;

    public CreateUserRequest(Long id, String user) {
        this.id = id;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
