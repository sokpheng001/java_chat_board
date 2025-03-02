package view;

import bean.ChatConnectionBean;
import bean.UserBean;
import model.dto.CreateChatConnectionUsingUserNameDto;
import model.dto.ResponseUserDto;
import utils.WriteDataForVerifyLoginStatus;

import java.util.Scanner;

public class UIWithAccount {
    // get login user info
    private static final String userUuidFromLogin = String.valueOf(WriteDataForVerifyLoginStatus.isLogin());
    private static final ResponseUserDto loginUser = UserBean.userController.getUserByUuid(userUuidFromLogin);
    public static void home(){
        // get welcome
        System.out.println("---");
        String welcome = "🌟 Welcome Again, " + loginUser.name() + "! 🌟";
        try{
            for(int i=0;i<welcome.length();i++){
                Thread.sleep(100);
                System.out.print(welcome.charAt(i));
            }
            System.out.println();
        }catch (Exception exception){
            System.out.println("[+] Welcome exception: " + exception.getMessage());
        }
        //
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
                // start adding connection
                ResponseUserDto userWantedToConnect = UserBean.userController.getUserByName(username);
                if(userWantedToConnect!=null){
                    int result = ChatConnectionBean.controller.createConnection(CreateChatConnectionUsingUserNameDto.builder()
                                    .loginUserName(loginUser.name())
                                    .wantedToConnectedUserName(userWantedToConnect.name())
                            .build());
//                    System.out.println("You " + loginUser.name());
//                    System.out.println("Person you wanted to connect " + userWantedToConnect.name());
                }else {
                    System.out.println("[!] User you wanted to connection is not found :(");
                }
            }
            case 3->{}
            case 4 ->{
                System.out.print("[*] Are you sure you want to logout? (y/n): ");
                String answer = new Scanner(System.in).nextLine();
                if(answer.equals("y")){
                    WriteDataForVerifyLoginStatus.writeDataOfStatusToFile("null");
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
