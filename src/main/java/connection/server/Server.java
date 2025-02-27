package connection.server;

import utils.LoadingFileData;

import java.io.*;
import java.net.*;
import java.util.Properties;

public class Server {
    private final static Properties properties = LoadingFileData.loadingProperties();
    public static void startServer() {
        System.out.println("[*] Started network.server  running in background...");
        Thread thread = new Thread(() -> {
            try {
                assert properties != null;
                try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("server_port")))) {
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("[+] Client IP connected: " + clientSocket.getRemoteSocketAddress());
                        // receive data from client
                        BufferedReader in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        System.out.println(in.readLine());
                    }
                }
            } catch (IOException e) {
                System.out.println("[!] Server Error: " + e.getMessage());
            }
        }
        );
        // start  thread as daemon mean it run in the background process
        thread.setDaemon(true);
        thread.start();
    }

}
