package connection;

import model.dto.ResponseUserDto;

import java.io.*;
import java.net.*;
import java.time.Instant;
import java.util.Date;

public class Client {
    public static boolean testConnection(String host, int port) {
        try(Socket socket = new Socket(host, port)){
            System.out.println("✅ Connection to server established.");
            return true;
        }catch (IOException e){
            System.out.println("⚠ Connection to server failed.");
            return false;
        }
    }
    public void getClientSocket(String host, int port,boolean isLogin, ResponseUserDto responseUserDto) {
        System.out.println("[+] Connected to server " + host + " on port " + port);
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
            // send data to server
            if(!isLogin){
                out.println("Hello Server, I am new user: " +  responseUserDto.name());
            }else {
                out.println("User [" + responseUserDto.name() + "] has been login at " + Date.from(Instant.now()));
            }
        } catch (IOException e) {
            System.out.println("[!] Error during network connection: " + e.getMessage());
        }
    }
}
