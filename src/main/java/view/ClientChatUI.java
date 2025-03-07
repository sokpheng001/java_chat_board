package view;

import bean.UserBean;
import lombok.Data;
import model.dto.ResponseUserDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ClientChatUI implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private static final Map<String, ClientChatUI> clientHandlers = new HashMap<>();  // Mapping client login (username) to their handler

    // Constructor for ClientChatUI
    public ClientChatUI(Socket socket, String senderName) throws IOException {
        this.clientSocket = socket;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);  // Ensure auto-flush
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        username = senderName;
    }
    @Override
    public void run() {
        try {
            // Register client handler in the server
            synchronized (clientHandlers) {
                clientHandlers.put(username, this);
            }
            System.out.println(clientHandlers.size());
            String message;
            while ((message = in.readLine()) != null) {
                // Forward the message to other clients (except sender)
                forwardMessageToOtherClients(username, message);
            }
        } catch (IOException e) {
            System.out.println("[!] Error retrieve message from client: " + e.getMessage());

//        } finally {
//            // Remove the client from the active list and close the connection
//            try {
//                synchronized (clientHandlers) {
//                    clientHandlers.remove(username);
//                    System.out.println("[+] Client disconnected.");
//                }
//                clientSocket.close();
//                System.out.println("---");
//            } catch (IOException e) {
//                System.out.println("[!] Error during disconnecting client: " + e.getMessage());
//            }
        }
    }

    // Method to forward the message to other clients (excluding the sender)
    private void forwardMessageToOtherClients(String sender, String message) throws IOException {
        out.println(clientHandlers.size());
        synchronized (clientHandlers) {
            for (Map.Entry<String, ClientChatUI> entry : clientHandlers.entrySet()) {
                ClientChatUI recipientHandler = entry.getValue();
                // Send message to all clients except the sender
                if (!recipientHandler.username.equals(sender)) {
                    recipientHandler.out.println("[" + sender + "]: " + message);
                }
            }
        }
    }
}
