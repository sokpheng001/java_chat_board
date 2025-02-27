package view;

import bean.UserBean;
import connection.client.Client;
import controller.UserController;
import model.dto.RegisterDto;
import model.dto.ResponseUserDto;
import utils.GetMachineIP;

import utils.WriteDataForVerifyLoginStatus;

import java.util.Scanner;

public class UIWithoutAccount {
    private static RegisterDto getRegisterDto() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("[+] Insert your name (Unique): ");
        String name = new Scanner(System.in).nextLine();
        System.out.print("[+] Insert your email address: ");
        String email = new Scanner(System.in).nextLine();
        System.out.print("[+] Insert your password: ");
        String password = new Scanner(System.in).nextLine();
        return new RegisterDto(name, email, password);
    }
    public static void home(){

        System.out.println("""
                -------------------------
                Welcome to the Chat_Board
                -------------------------
                1. Register
                2. Login
                3. Exit
                -------------------""");
        System.out.print("[+] Insert option: ");
        int opt = new Scanner(System.in).nextInt();
        switchOpt (opt);
    }
    private static void switchOpt(int opt){
        switch (opt){
            case 1 ->{
                ResponseUserDto responseUserDto = UserBean.userController.register(getRegisterDto());
                WriteDataForVerifyLoginStatus.writeDataOfStatusToFie(responseUserDto.uuid());
                new Client().getClient(GetMachineIP.getMachineIP(),1234);
                System.out.println("[+] User data created: " + responseUserDto);
            }
        }
    }
}
