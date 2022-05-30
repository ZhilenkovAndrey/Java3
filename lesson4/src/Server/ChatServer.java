package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {

    private ServerSocket socket;
    private final AuthenticationService authenticationService;
    private Set<ClientHandler> loggedClients;
    ExecutorService clientThread;

    public ChatServer() {
        clientThread = Executors.newCachedThreadPool();
        try {
            authenticationService = new AuthenticationService();
            loggedClients = new HashSet<>();
            this.socket = new ServerSocket(8888);

            while (true) {
                System.out.println("Waiting for a new connection...");
                Socket client = socket.accept();
                System.out.println("Client accepted.");
                clientThread.execute(() -> new ClientHandler(client, this));
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
            sendMessageToUser(message);
            saveUserMessage(message);
        } else {
            broadcastMessage(message);
        }
    }

    public synchronized void broadcastMessage(String message) {
        loggedClients.stream()
                .forEach(ch -> ch.sendMessage(message));
        saveToAllMessage(message);
    }

    public synchronized void sendMessageToUser(String message) {
        loggedClients.stream()
                .filter(c -> message.split(" ", 2)[1].startsWith("/w " + c.getName()))
                .forEach(c -> c.sendMessage(message.substring(0, message.indexOf(" "))
                        + message.substring(message.lastIndexOf(c.getName()) + c.getName().length())));
    }

    public synchronized void saveUserMessage(String message) {
        loggedClients.stream()
                .filter(c -> message.split(" ", 2)[1].startsWith("/w " + c.getName()))
                .forEach(c -> c.fileOutputStream(message.substring(0, message.indexOf(" "))
                        + message.substring(message.lastIndexOf(c.getName()) + c.getName().length())));
    }

    public synchronized void saveToAllMessage(String message) {
        loggedClients.stream()
                .forEach(c -> c.fileOutputStream(message));
    }
}

