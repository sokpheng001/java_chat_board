package connection;

import model.User;
import model.dto.ResponseUserDto;

import java.io.*;
import java.net.*;

public class Client {
    public static boolean testConnection(String host, int port) {
        try(Socket socket = new Socket(host, port)){
            System.out.println("✅ Connection to server established.");
            return true;
        }catch (IOException e){
            System.out.println("⚠️ Connection to server failed.");
            return false;
        }
    }
    public void getClient(String host, int port, ResponseUserDto responseUserDto) {
        System.out.println("[+] Connecting to server " + host + " on port " + port);
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
            // send data to server
            out.println("=> Hello Server, I am " +  responseUserDto.name());
        } catch (IOException e) {
            System.out.println("[!] Error during network connection: " + e.getMessage());
        }
    }
}
