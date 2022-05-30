package Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientHandler {

    private final Socket socket;
    private final ChatServer server;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String name;
    private final Logger log = LogManager.getLogger(ClientHandler.class);

    public ClientHandler(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            log.throwing(ex);
            log.info("Something went wrong during a client connection.");
            throw new RuntimeException(ex);
        }
        doAuthentication();
        listenMessages();
    }

    public String getName() {
        return name;
    }

    private void doAuthentication() {
        try {
            performAuthentication();
        } catch (IOException ex) {
            log.throwing(ex);
            log.info("Something went wrong during a client authentication.");
            throw new RuntimeException(ex);
        }
    }

    private void performAuthentication() throws IOException {
        while (true) {
            String inboundMessage = in.readUTF();
            if (inboundMessage.startsWith("-auth")) {
                String[] credentials = inboundMessage.split("\\s");

                AtomicBoolean isSuccess = new AtomicBoolean(false);
                server.getAuthenticationService()
                        .findUsernameByLoginAndPassword(credentials[1], credentials[2])
                        .ifPresentOrElse(
                                username -> {
                                    if (!server.isUsernameOccupied(username)) {
                                        server.broadcastMessage(String.format("User[%s] is logged in", username));
                                        log.info("User[%s] is logged in", username);
                                        name = username;
                                        server.addClient(this);
                                        isSuccess.set(true);
                                    } else {
                                        log.info("Current username is already occupied.");
                                        sendMessage("Current username is already occupied.");
                                    }
                                },
                                () -> {
                                    log.info("Bad credentials.");
                                    sendMessage("Bad credentials.");
                                }
                        );
                sendMessage("You successfully logged-in.\n" +
                        "you can enter '/exit' to live chat");
                log.info("Client logged-in.");
                loadMessages(fileInputStreamList());

                if (isSuccess.get()) break;
            } else {
                log.info("Client not logged-in.");
                sendMessage("You need to be logged-in.");
            }
        }
    }

    public void sendMessage(String outboundMessage) {
        try {
            out.writeUTF(outboundMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage() {

        try {
            String inboundMessage = in.readUTF();
            if (inboundMessage.equals("/exit")) {
                exit();
            } else {
                server.broadcastMessageToUser("[" + name + "] " + inboundMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenMessages() {
        while (true) {
            readMessage();
        }
    }

    public void exit() {
        sendMessage("You successfully logged-out.");
        try {
            out.close();
        } catch (IOException ex) {
            log.throwing(ex);
        }
        try {
            in.close();
        } catch (IOException ex) {
            log.throwing(ex);
        }
        try {
            socket.close();
        } catch (IOException ex) {
            log.throwing(ex);
        }
        server.removeClient(this);
        server.clientThread.shutdown();
        log.info("Client successfully logged-out.");
    }

    public void fileOutputStream(String message) {
        try (FileOutputStream fileOut = new FileOutputStream(
                "C:/Users/ASUS/IdeaProjects/Java3/lesson3/src/messages/" + name + ".txt", true)) {
            fileOut.write((message + "\n").getBytes());
        } catch (IOException e) {
            log.throwing(e);
            log.info("Something went wrong during writing client chat history.");
            throw new RuntimeException(e);
        }
    }

    public List<String> fileInputStreamList() {
        String c;
        List<String> fileList = new ArrayList<>();
        FileInputStream fin;
        try {
            fin = new FileInputStream(
                    "C:/Users/ASUS/IdeaProjects/Java3/lesson3/src/messages/" + name + ".txt");
        } catch (IOException e) {
            log.throwing(e);
            log.info("Something went wrong during ridding client chat history.");
            throw new RuntimeException(e);
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fin));
        try {
            while ((c = bufferedReader.readLine()) != null) {
                fileList.add(c);
            }
            fin.close();
            return fileList;
        } catch (IOException e) {
            log.throwing(e);
            log.info("Something went wrong during ridding client chat history.");
            throw new RuntimeException(e);
        }
    }

    public void loadMessages(List<String> fileList) {
        int chatSize = 100;

        if (fileList.size() > chatSize) {
            for (int i = fileList.size() - chatSize; i < fileList.size(); i++) {
                try {
                    out.writeUTF(fileList.get(i));
                } catch (IOException e) {
                    log.throwing(e);
                    log.info("Something went wrong during sending to client chat history.");
                    e.printStackTrace();
                }
            }
        } else {
            for (int i = 0; i < fileList.size(); i++) {
                try {
                    out.writeUTF(fileList.get(i));
                } catch (IOException e) {
                    log.throwing(e);
                    log.info("Something went wrong during sending to client chat history.");
                    e.printStackTrace();
                }
            }
        }
    }
}
