package connection;

import java.io.*;
import java.net.*;

public class Client {
    public void getClient(String host, int port) {
        System.out.println("[+] Connecting to server " + host + " on port " + port);
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
            // send data to server
            out.println("=> Hello Server");
        } catch (IOException e) {
            System.out.println("[!] Error during network.client connection: " + e.getMessage());
        }
    }
}
