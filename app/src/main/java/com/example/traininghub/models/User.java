package com.example.traininghub.models;

public class User {

    private String email;
    private String name;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
