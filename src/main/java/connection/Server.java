package connection;

import bean.UserBean;
import client.service.UserServiceImpl;
import model.dto.ResponseUserDto;
import server.repository.ServerRepository;
import utils.CheckTime;
import utils.GetMachineIP;
import utils.LoadingFileData;
import utils.WriteDataForVerifyLoginStatus;

import java.io.*;
import java.net.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Properties;

public class Server {
    private final static Properties properties = LoadingFileData.loadingProperties();
    private final static ResponseUserDto currentUser = UserBean.userController
            .getUserByUuid(String.valueOf(WriteDataForVerifyLoginStatus.isLogin()));

    // Store active clients
    private static final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();

    public void startServer() {
        try {
            assert properties != null;
            int serverPort = Integer.parseInt(properties.getProperty("server_port"));
            String serverIpAddress = GetMachineIP.getMachineIP();

            try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
                int serverId = ServerRepository.insertServerIPAddressToDbAndReturnServerRowId(serverIpAddress, serverPort);
                if (serverId > 0) {
                    System.out.println("[+] Server info successfully added to database");
                    System.out.println("[*] Started Server Socket...");
                    System.out.println("---");
                    System.out.println("[+] Server IP Address: " + serverIpAddress);
                    System.out.println("[+] Server Port: " + serverPort);
                    System.out.println("---");

                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("[+] Client IP connected: " + clientSocket.getInetAddress());
                        System.out.println("[+] Client Port: " + clientSocket.getPort());
                        System.out.println("---");
                        //
                        // Start a new thread for the client
                        ClientHandler clientHandler = new ClientHandler(clientSocket);
                        clients.add(clientHandler);
                        new Thread(clientHandler).start();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("[!] Server Error: " + e.getMessage());
        }
    }

    class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private PrintWriter out;
        private String username;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                this.out = out;

                // Read username
                username = in.readLine();
                if (username == null || username.isEmpty()) {
                    System.out.println("[!] Username is null or empty, disconnecting client.");
                    out.println("User not found.");
                    clientSocket.close();
                    return;
                }

                System.out.println("[+] User connected to server: " + username);
                Optional<ResponseUserDto> user = new UserServiceImpl().findAllUsers().stream()
                        .filter(e -> e.name().equals(username))
                        .findFirst();
                if (user.isPresent()) {
                    System.out.println("[+] User [" + username + "] has joined the chat at " + Date.from(Instant.now()));
                    System.out.println("---");
                    // hello to client
//                    out.println(CheckTime.checkTimeOfDay(username));
                    out.println("Glad to see you");
                    out.println("...............");
                    System.out.println("---");
                    sendToAll("[" + username + "] has joined the chat at " + Date.from(Instant.now()), this);
                } else {
                    System.out.println("[!] User not found in database. Disconnecting...");
                    out.println("[!] User not found.");
                    clientSocket.close();
                    return;
                }

                // Read and broadcast messages
                String message;
                while ((message = in.readLine()) != null) {
                    if (!message.isEmpty()) {
                        System.out.println("[*] Message from [" + username + "]: " + message);
                        sendToAll("[" + username + "]: " + message, this);
                    }
                }

            } catch (IOException e) {
                System.out.println("[!] Error while handling client [" + username + "]: " + e.getMessage());
            } finally {
                // Remove client on disconnect
                clients.remove(this);
                sendToAll("[" + username + "] has left the chat at " + Date.from(Instant.now()), this);
                System.out.println("[!] User [" + username + "] has disconnected.");
                System.out.println("[+] TimeStamp: " + Date.from(Instant.now()));
                System.out.println("---");
                try {
                    clientSocket.close();
                } catch (IOException ignored) {}
            }
        }

        // Broadcast message to all except sender
        private void sendToAll(String message, ClientHandler sender) {
            for (ClientHandler client : clients) {
                if (client != sender) { // Skip sender
                    client.out.println(message);
                }
            }
        }
    }
}
