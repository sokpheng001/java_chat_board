package connection;

import bean.UserBean;
import client.service.UserServiceImpl;
import client.service.abstraction.UserService;
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
import java.util.List;
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

                        // Initialize input and output streams
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        // Read the username (first message from client)
                        String username = in.readLine();
                        System.out.println("[+] Server Username: " + username);
                        Optional<ResponseUserDto> user = new UserServiceImpl().findAllUsers().stream()
                                .filter(e -> e.name().equals(username))
                                .findFirst();
                        if (user.isPresent()) {
                            senderName = user.get().name();
                            System.out.println("[+] User [" + senderName + "] has joined the chat at " + Date.from(Instant.now()));
                            // Inform the client that they have joined
                            out.println("->[Server]:  Hello, " + senderName );
                        } else {
                            out.println("User not found.");
                        }

                        // Read and print further messages from client
                        String message;
                        while ((message = in.readLine()) != null) {
                            if (!message.isEmpty()) {
                                System.out.println("[*] Message from client: " + message);
                            }
                        }
                        // Timestamp for the connection
                        System.out.println("[+] TimeStamp: " + Date.from(Instant.now()));
                        System.out.println("---");

                        // Start chat interface for the client
                        ClientChatUI clientChatUI = new ClientChatUI(clientSocket, senderName);
                        new Thread(clientChatUI).start();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("[!] Server Error: " + e.getMessage());
        }
    }
}
