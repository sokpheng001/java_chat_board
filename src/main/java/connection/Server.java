package connection;

import bean.UserBean;
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
import java.util.Properties;

public class Server {
    private final static Properties properties = LoadingFileData.loadingProperties();
    private final static ResponseUserDto currentUser = UserBean.userController
            .getUserByUuid(String.valueOf(WriteDataForVerifyLoginStatus.isLogin()));
    public static void startServer() {
        try {
            assert properties != null;
            int serverPort = Integer.parseInt(properties.getProperty("server_port"));
            String serverIpAddress = GetMachineIP.getMachineIP();
            try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
                // set server ip to database for all clients use
                int serverId = ServerRepository.insertServerIPAddressToDbAndReturnServerRowId(serverIpAddress, serverPort);
                if(serverId>0){
                    System.out.println("[+] Server info successfully in database");
                    System.out.println("[*] Started Server Socket...");
                    System.out.println("---");
                    System.out.println("[+] Server IP Address: " + serverIpAddress);
                    System.out.println("[+] Server Port: " + serverPort);
                    System.out.println("---");
                    //
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("[+] Client IP connected: " + clientSocket.getInetAddress());
                        System.out.println("[+] Client Port: " + clientSocket.getPort());
                        // receive data from client
                        BufferedReader in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        if(in.readLine()!=null){
                            System.out.println("[*] Message from client: " + in.readLine());
                        }
                        System.out.println("[+] TimeStamp: " + Date.from(Instant.now()));
                        System.out.println("---");
                        // start chat
                        ClientChatUI clientChatUI = new ClientChatUI(clientSocket);
                        new Thread(clientChatUI).start();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("[!] Server Error: " + e.getMessage());
        }
    }

}
