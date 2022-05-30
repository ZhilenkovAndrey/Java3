package ru.geekbrains.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {

    private ServerSocket socket;
    private final AuthenticationService authenticationService;
    private Set<ClientHandler> loggedClients;

    public ChatServer() {
        try {
            authenticationService = new AuthenticationService();
            loggedClients = new HashSet<>();
            this.socket = new ServerSocket(8888);

            while (true) {
                System.out.println("Waiting for a new connection...");
                Socket client = socket.accept();
                System.out.println("Client accepted.");
                new Thread(() -> new ClientHandler(client, this)).start();
            }

        } catch (IOException e) {
            throw new RuntimeException("Something went wrong during connection establishing.", e);
        }
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public synchronized void addClient(ClientHandler client) {
        loggedClients.add(client);
    }

    public synchronized void removeClient(ClientHandler client) {
        loggedClients.remove(client);
    }

    public synchronized boolean isUsernameOccupied(String username) {
        return loggedClients.stream()
                .anyMatch(c -> c.getName().equals(username));
    }

    public synchronized void broadcastMessageToUser(String message) {
        if (loggedClients.stream().anyMatch(c -> message.split(" ",2)[1].startsWith("/w " + c.getName()))) {
            loggedClients.stream()
                    .filter(c -> message.split(" ", 2)[1].startsWith("/w " + c.getName()))
                    .forEach(c -> c.sendMessage(message.substring(0, message.indexOf(" "))
                            + message.substring(message.lastIndexOf(c.getName()) + c.getName().length())));
        } else {
            broadcastMessage(message);
        }
    }
    public synchronized void broadcastMessage(String message) {
        loggedClients.stream()
                .forEach(ch -> ch.sendMessage(message));
    }

}

