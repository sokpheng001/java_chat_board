package connection;

import server.repository.ServerRepository;
import utils.GetMachineIP;
import utils.LoadingFileData;

import java.io.*;
import java.net.*;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;

public class Server {
    private final static Properties properties = LoadingFileData.loadingProperties();
    public static void startServer() {
//        System.out.println("[*] Started server running in background...");
        Thread thread = new Thread(() -> {
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
                            System.out.println("[->] Message from client: " + in.readLine());
                            System.out.println("TimeStamp: " + Date.from(Instant.now()));
                            System.out.println("---");
                        }
                    }

                }
            } catch (IOException e) {
                System.out.println("[!] Server Error: " + e.getMessage());
            }
        }
        );
        // start  thread as daemon mean it run in the background process
//        thread.setDaemon(true);
        thread.start();
    }

}
