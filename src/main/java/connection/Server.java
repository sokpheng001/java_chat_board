package connection;

import bean.UserBean;
import client.service.UserServiceImpl;
import model.User;
import model.dto.CreateChatMessageDto;
import model.dto.ResponseUserDto;
import server.repository.ServerRepository;
import utils.GetMachineIP;
import utils.LoadingFileData;
import utils.WriteDataForVerifyLoginStatus;
import java.io.*;
import java.net.*;
import java.time.Instant;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Properties;

public class Server {
    private final static Properties properties = LoadingFileData.loadingProperties();
    private final static ResponseUserDto currentUser = UserBean.userController
            .getUserByUuid(String.valueOf(WriteDataForVerifyLoginStatus.isLogin()));
    private String receiver;
    private User senderUser;
    private User receiverUser;

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
                // Read username of sender for the first time
                username = in.readLine();
                if (username == null || username.isEmpty()) {
                    System.out.println("[!] Username is null or empty, disconnecting client.");
                    out.println("User not found.");
                    clientSocket.close();
                    return;
                }
                //
                System.out.println("[+] User connected to server: " + username);
                findUserByName(username); // verify the sender existed in database
                senderUser = UserBean.userRepository.findByUsername(username);
                // read receiver from client send
                receiver = in.readLine();
                System.out.println("Receiver: " + receiver);
                receiverUser= UserBean.userRepository.findByUsername(receiver);
                // Read and broadcast messages, and create message connection
                CreateChatMessageDto createChatMessageDto;
                String message;
                while ((message = in.readLine()) != null) {
                    if (!message.isEmpty()) {
                        createChatMessageDto  =
                                CreateChatMessageDto.builder()
                                        .uuid(UUID.randomUUID().toString())
                                        .senderId(Math.toIntExact(senderUser.getId()))
                                        .receiverId(Math.toIntExact(receiverUser.getId()))
                                        .message(message)
                                        .build();
                        System.out.println("[*] Message from [" + username + "]: " + message);
                        sendToAll("[" + username + "]: " + message, this);
                        System.out.println(createChatMessageDto);
                    }
                }

            } catch (IOException e) {
                System.out.println("[!] Error while handling client [" + username + "]: " + e.getMessage());
            } finally {
                // Remove client on disconnect
                clients.remove(this);
                sendToAll("[" + username + "] has left the chat at " + Date.from(Instant.now()), this);
                System.out.println("[!] User [" + username + "] has disconnected at " + Date.from(Instant.now()));
                System.out.println("[+] TimeStamp: " + Date.from(Instant.now()));
                System.out.println("---");
                try {
                    clientSocket.close();
                } catch (IOException ignored) {}
            }
        }
        private Optional<ResponseUserDto> findUserByName(String username) throws IOException {
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
                return null;
            }
            return user;
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
