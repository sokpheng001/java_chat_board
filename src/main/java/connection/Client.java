package connection;

import model.dto.ResponseUserDto;
import view.ClientChatUI;

import java.io.*;
import java.net.*;
import java.time.Instant;
import java.util.Date;

public class Client {

    public static boolean testConnection(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            System.out.println("✅ Connection to server established.");
            return true;
        } catch (IOException e) {
            System.out.println("⚠ Connection to server failed.");
            return false;
        }
    }
    private void chatUI(String chatter) {
        System.out.println("-".repeat(30));
        System.out.println("Enter 'exit' to quit)");
        System.out.println("-".repeat(30));
        System.out.println("Chat with User: [" + chatter + "]");
        System.out.println("-".repeat(30));
    }

    // Method for the initial connection to the server (for login or registration)
    public void getLoginSocket(String host, int port, boolean isLogin, ResponseUserDto responseUserDto) {
        System.out.println("[+] Connected to server " + host + " on port " + port);
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            // Send login or registration info to the server
            if (!isLogin) {
                out.println("Hello Server, I am a new user: " + responseUserDto.name());
            } else {
                out.println("User [" + responseUserDto.name() + "] has logged in at " + Date.from(Instant.now()));
            }
        } catch (IOException e) {
            System.out.println("[!] Error during network connection: " + e.getMessage());
        }
    }

    // Method to start the chat functionality
    public void getClientChatSocket(String host, int port, String sender,ResponseUserDto receiver) {

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()), 1);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            // Send login info when the user connects
            out.println("User [" + sender + "] has joined the chat at " + Date.from(Instant.now()));

//            out.flush();
            // Thread to receive messages from the server
            Thread listenThread = new Thread(() -> {
                try {
                    System.out.println(in.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    while (true) {
                        if(in.ready()){
                            String message = in.readLine();
                            System.out.print(message);
                        }
                        // Display incoming messages from the server (i.e., messages from other clients)
                    }
                } catch (IOException e) {
                    System.out.println("[!] Error while receiving messages: " + e.getMessage());
                }
            });
            listenThread.start();
            // Loop to send messages to the server
            chatUI(receiver.name());
            String message;
            while (true) {
                System.out.print("[You]: ");
                message = consoleInput.readLine();
                if ("exit".equalsIgnoreCase(message)) {
                    out.println("User [" + sender + "] has left the chat.");
                    out.close();
                    return;
                }
                // Send the message to the server
                out.println(message);
            }
        } catch (IOException e) {
            System.out.println("[!] Error during network connection: " + e.getMessage());
        }
    }
}
