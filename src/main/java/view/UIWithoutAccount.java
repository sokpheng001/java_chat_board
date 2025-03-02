package view;

import bean.UserBean;
import client.controller.AuthController;
import connection.Client;
import model.User;
import model.dto.UserRegisterDto;
import model.dto.ResponseUserDto;
import utils.GetMachineIP;

import utils.LoadingFileData;
import utils.WriteDataForVerifyLoginStatus;
import utils.validation.CustomizeValidator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UIWithoutAccount {
    private final static AuthController authController = new AuthController();
    private final static Properties properties = LoadingFileData.loadingProperties();
    private static  int serverPort ;
    private static String serverIpAddress;
    static {
        assert properties != null;
        serverPort = Integer.parseInt(properties.getProperty("server_port"));
        serverIpAddress = GetMachineIP.getMachineIP();
    }

    private static UserRegisterDto getRegisterDto() {
        String name=null, email=null, password=null;
        while (name==null || email==null || password==null) {
            if(name==null){
                System.out.print("[+] Insert your name (Unique): ");
                name = new Scanner(System.in).nextLine();
            }
            if(email==null){
                System.out.print("[+] Insert your email address: ");
                email = new Scanner(System.in).nextLine();
                if(CustomizeValidator.isValidEmail(email)){
                    if(password==null){
                        System.out.print("[+] Insert your password: ");
                        password = new Scanner(System.in).nextLine();
                    }
                }else {
                    System.out.println("🔥 Invalid Email Address");
                    email = null;
                }
            }

        }
        return new UserRegisterDto(name, email, password, Date.valueOf(LocalDate.now()));
    }
    public static void home(){

        System.out.println("----");
        // check if client has been connected to client
        assert properties != null;
        if(Client.testConnection(serverIpAddress,serverPort)){
            // check if user has been login
            if(!Objects.equals(String.valueOf(WriteDataForVerifyLoginStatus.isLogin()), "null")){
                UIWithAccount.home();
                return;
            }
            while (true){
                System.out.println("""
                ===================================
                  Welcome to the Local Chat_Board
                ===================================
                1. Register
                2. Login
                3. Exit
                -------------------""");
                System.out.print("[+] Insert option: ");
                int opt = new Scanner(System.in).nextInt();
                switchOpt (opt);
            }
        }else {
            System.exit(0);
        }

    }
    private static void pressToNext(){
        System.out.print("> Press enter to continue: ");
        new Scanner(System.in).nextLine();
    }
    private static void switchOpt(int opt){
        switch (opt){
            case 1 ->{
                // register
                System.out.println("----");
                ResponseUserDto responseUserDto = UserBean.userController.register(getRegisterDto());
                if(responseUserDto!=null){
                    // write user uuid to file in order to check if user has been login or not
                    WriteDataForVerifyLoginStatus.writeDataOfStatusToFile(responseUserDto.uuid());
                    // connection new client to the server
                    new Client().getClientSocket(serverIpAddress,serverPort,false ,responseUserDto);
                    System.out.println("------\n[+] User data created: " + responseUserDto);
                    UIWithAccount.home();
                }
                pressToNext();
            }
            case 2 ->{
                System.out.println("----");
                System.out.print("[+] Insert username: ");
                String username = new Scanner(System.in).nextLine();
                System.out.print("[+] Insert password: ");
                String password = new Scanner(System.in).nextLine();
                if(authController.login(username, password)!=null){
                    ResponseUserDto responseUserDto = UserBean.userController.getUserByName(username);
//                    // write user uuid to file in order to check if user has been login or not
                    WriteDataForVerifyLoginStatus.writeDataOfStatusToFile(responseUserDto.uuid());
//                    // get connection to server
                    new Client().getClientSocket(serverIpAddress,serverPort,true ,responseUserDto);
                    UIWithAccount.home();
                }else{
                    System.out.println("☹ Login failed");
                }
                pressToNext();
            }
            case 3 ->{
                System.out.print("[*] Are you sure you want to exit? (y/n): ");
                String answer = new Scanner(System.in).nextLine();
                if(answer.equals("y")){
                    System.out.println("\uD83D\uDC4B System closed");
                    System.exit(0);
                }
            }
        }

    }
}
