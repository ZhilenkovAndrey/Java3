package ru.geekbrains.server;

public record Customer (long id, String userName, String login, String password) {

    public Customer( String userName, String login, String password) {
        this(0,  userName, login, password);
    }
}


