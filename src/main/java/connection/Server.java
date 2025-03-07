package connection;

import bean.UserBean;
import client.service.UserServiceImpl;
import model.dto.ResponseUserDto;
import server.repository.ServerRepository;
import utils.GetMachineIP;
import utils.LoadingFileData;
import utils.WriteDataForVerifyLoginStatus;
import view.ClientChatUI;

import java.io.*;
import java.net.*;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

public class Server {
    private final static Properties properties = LoadingFileData.loadingProperties();
    private final static ResponseUserDto currentUser = UserBean.userController
            .getUserByUuid(String.valueOf(WriteDataForVerifyLoginStatus.isLogin()));
    private String senderName = null;

    public void startServer() {
        try {
            assert properties != null;
            int serverPort = Integer.parseInt(properties.getProperty("server_port"));
            String serverIpAddress = GetMachineIP.getMachineIP();

            try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
                // Set server IP to database for all clients to use
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
                        // Start a new thread to handle the client communication
                        new Thread(new ClientHandler(clientSocket)).start();
                        // Start chat interface for the client
                        new Thread(new ClientChatUI(clientSocket, senderName)).start();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("[!] Server Error: " + e.getMessage());
        }
    }

    // Inner class to handle each client connection
    class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        // Inside the ClientHandler class
        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                // Read the username (first message from client)
                String username = in.readLine();
                if (username == null || username.isEmpty()) {
                    System.out.println("[!] Username is null or empty, disconnecting client.");
                    out.println("User not found.");
                    clientSocket.close();
                    return;
                }
                System.out.println("[+] Server Username: " + username);
                // Fetch user from database
                Optional<ResponseUserDto> user = new UserServiceImpl().findAllUsers().stream()
                        .filter(e -> e.name().equals(username))
                        .findFirst();

                if (user.isPresent()) {
                    String senderName = user.get().name(); // Get user's name
                    System.out.println("[+] User [" + senderName + "] has joined the chat at " + Date.from(Instant.now()));
                    System.out.println("---");
                    out.println("[Server]:  Hello, " + senderName); // Send a greeting to the client
                } else {
                    out.println("User not found.");
                    clientSocket.close();
                    return;
                }

                // Read and handle further messages from the client
                String message;
                while ((message = in.readLine()) != null) {
                    if (!message.isEmpty()) {
                        System.out.println("[*] Message from client: " + message); // Print the message from client
                    }
                }

                // Timestamp for the connection end
                System.out.println("[+] TimeStamp: " + Date.from(Instant.now()));
                System.out.println("---");



            } catch (IOException e) {
                System.out.println("[!] Error while handling client: " + e.getMessage());
            }
        }

    }
}
