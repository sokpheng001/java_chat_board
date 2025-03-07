package connection;

import model.dto.ResponseUserDto;

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
        System.out.println("Chat with User: [" + chatter + "] at [" + Instant.now() + "]");
        System.out.println("-".repeat(30));
    }

    // Method for the initial connection to the server (for login or registration)
    public void getLoginSocket(String host, int port, boolean isLogin, ResponseUserDto responseUserDto) {
        System.out.println("[+] Connected to server " + host + " on port " + port);
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            // Send login or registration info to the server
            if (!isLogin) {
//                out.println(responseUserDto.name());
                out.println("Hello Server, I am a new user: " + responseUserDto.name());
            } else {
                out.println("User [" + responseUserDto.name() + "] has logged in at " + Date.from(Instant.now()));
            }
        } catch (IOException e) {
            System.out.println("[!] Error during network connection: " + e.getMessage());
        }
    }

    // Method to start the chat functionality
    public void getClientChatSocket(String host, int port, String sender, ResponseUserDto receiver) {
        // Declare isChatActive flag here to control the chat state
        boolean[] isChatActive = {true};
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            // Send login info when the user connects
            out.println(sender); // send name to server for identify who the user is
//            out.println("User [" + sender + "] has joined the chat at " + Date.from(Instant.now()));

            // Thread to receive messages from the server
            Thread listenThread = new Thread(() -> {
                try {
                    // Wait and display initial message
                    System.out.println(in.readLine());
                } catch (IOException e) {
                    System.out.println("[!] Error during network connection: " + e.getMessage());
                }

                try {
                    while (isChatActive[0]) {
                        if (in.ready()) {
                            String message = in.readLine();
                            System.out.println(message);  // Print incoming messages from other users
                        }
                    }
                } catch (IOException e) {
                    System.out.println("[!] Error while receiving messages: " + e.getMessage());
                }
            });

            listenThread.start();  // Start the listener thread

            // Loop to send messages to the server
            chatUI(receiver.name());
            String message;

            while (true) {
                System.out.print("[You]: ");
                message = consoleInput.readLine();
                if ("exit".equalsIgnoreCase(message)) {
                    out.println("User [" + sender + "] has left the chat.");
                    isChatActive[0] = false;  // Set isChatActive to false to stop the chat
                    break;
                }
                // Send the message to the server
                out.println(message);
            }

            // Close the socket after chat ends
            socket.close();

        } catch (IOException e) {
            System.out.println("[!] Error during network connection: " + e.getMessage());
        }
    }
}
