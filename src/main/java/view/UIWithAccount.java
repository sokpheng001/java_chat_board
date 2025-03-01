package view;

import bean.UserBean;
import model.dto.ResponseUserDto;
import utils.WriteDataForVerifyLoginStatus;

import java.util.Scanner;

public class UIWithAccount {
    public static void home(){
        while (true){
            System.out.println("""
                ===================================
                  Welcome to the Local Chat_Board
                ===================================
                1. Select a user to chat
                2. Add a new connection
                3. View chat history
                4. Logout
                5. Exit
                ----""");
            System.out.print("[+] Insert option: ");
            int opt = new Scanner(System.in).nextInt();
            switchOpt(opt);
        }
    }
    private static void switchOpt(int opt){
        switch (opt){
            case 1 ->{
                System.out.println("----");

                System.out.println("----");
            }case 2 ->{
                System.out.println("----");
                System.out.print("[+] Insert connector name: ");
                String username = new Scanner(System.in).nextLine();
                ResponseUserDto responseUserDto = UserBean.userController.getUserByName(username);
                System.out.println(responseUserDto.name());
            }
            case 3->{}
            case 4 ->{
                System.out.print("[*] Are you sure you want to logout? (y/n): ");
                String answer = new Scanner(System.in).nextLine();
                if(answer.equals("y")){
                    WriteDataForVerifyLoginStatus.writeDataOfStatusToFile(null);
                    UIWithoutAccount.home();
                }
            }
            case 5 ->{
                System.out.print("[*] Are you sure you want to exit? (y/n): ");
                String answer = new Scanner(System.in).nextLine();
                if(answer.equals("y")){
                    System.out.println("\uD83D\uDC4B Back to Home");
                    System.exit(0);
                    UIWithoutAccount.home();
                }
            }
        }

    }
}
