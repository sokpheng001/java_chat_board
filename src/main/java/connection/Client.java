package connection;

import model.dto.ResponseUserDto;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class Client {
    private Socket socket;
    private boolean isServerResponse = false;

    public boolean testConnection(Socket clientSocket) {
        this.socket = clientSocket;
        System.out.println("✅ Connection to server established.");
        return true;
    }

    private void chatUI(String chatter) {
        System.out.println("-".repeat(70));
        System.out.println("Enter 'exit' to quit");
        System.out.println("-".repeat(70));
        System.out.println("Chat with User: [" + chatter + "] at [" + Date.from(Instant.now()) + "]");
        System.out.println("-".repeat(70));
    }

    public void getLoginSocket(boolean isLogin, ResponseUserDto responseUserDto) {
        System.out.println("[+] Connected to server " + this.socket.getInetAddress() + " on port " + this.socket.getPort());
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            if (!isLogin) {
                out.println("Hello Server, I am a new user: " + responseUserDto.name());
            } else {
                out.println("User [" + responseUserDto.name() + "] has logged in at " + Date.from(Instant.now()));
            }
        } catch (IOException e) {
            System.out.println("[!] Error during network connection: " + e.getMessage());
        }
    }

    public void getClientChatSocket(String host, int port, String sender, ResponseUserDto receiver) {
        boolean[] isChatActive = {true};
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            out.println(sender); // Send username to server

            Thread listenThread = new Thread(() -> {
                try {
                    System.out.println(in.readLine()); // Initial message from server
                    while (isChatActive[0]) {
                        if (in.ready()) {
                            String message = in.readLine();
                            System.out.print("\r" + " ".repeat(50) + "\r"); // Clear input line
                            System.out.println(message);
                            System.out.print("[You]: "); // Restore prompt
                        }
                    }
                } catch (IOException e) {
                    System.out.println("[!] Connection lost.");
                }
            });

            listenThread.start();
            chatUI(receiver.name());

            String message;
            while (isChatActive[0]) {
                if (!in.ready()) {
                    isServerResponse = true;
                    System.out.print("[You]: ");
                    message = consoleInput.readLine();
                    if ("exit".equalsIgnoreCase(message)) {
                        out.println("[!] User [" + sender + "] has left the chat.");
                        isChatActive[0] = false;;
                        break;
                    }
                    out.println(message);
                }
            }

            listenThread.interrupt();  // Stop listener thread on exit

        } catch (IOException e) {
            System.out.println("[!] Error during network connection: " + e.getMessage());
        }
    }
}
