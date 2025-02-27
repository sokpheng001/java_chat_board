package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final Map<String, Socket> clients = new HashMap<>();

    public static void startServer() {
        System.out.println("Starting server in Background...");
        Thread thread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(5555)) {
//                System.out.println("Chat Server started...");
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Remote address: " + socket.getRemoteSocketAddress());
                    new ClientHandler(socket).start();
                }
            } catch (IOException e) {
                System.out.println("Server Error: " + e.getMessage());
            }
        }
        );
        // start  thread as daemon mean it run in the background process
        thread.setDaemon(true);
        thread.start();
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;
        private String username;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Authentication
                out.println("Enter username:");
//                out.flush();
                username = in.readLine();

                synchronized (clients) {
                    if (clients.containsKey(username)) {
                        out.println("[!] Username already taken. Disconnecting.");
                        socket.close();
                        return;
                    }
                    clients.put(username, socket);
                }

                out.println("Welcome " + username + "! You can now chat.");
                broadcast(username + " has joined the chat.");

                // Listen for messages
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("exit")) {
                        break;
                    }
                    broadcast(username + ": " + message);
                }

                // Cleanup
                synchronized (clients) {
                    clients.remove(username);
                }
                socket.close();
                broadcast(username + " has left the chat.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void broadcast(String message) {
            synchronized (clients) {
                for (Socket clientSocket : clients.values()) {
                    try {
                        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                        writer.println(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
