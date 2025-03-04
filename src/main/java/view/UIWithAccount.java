package view;

import bean.ChatConnectionBean;
import bean.UserBean;
import connection.Client;
import model.dto.CreateChatConnectionUsingUserNameDto;
import model.dto.ResponseUserDto;
import org.nocrala.tools.texttablefmt.Table;
import utils.GetMachineIP;
import utils.LoadingFileData;
import utils.WriteDataForVerifyLoginStatus;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

public class UIWithAccount {
    // get login user info
    private static final String userUuidFromLogin = String.valueOf(WriteDataForVerifyLoginStatus.isLogin());
    private static final ResponseUserDto loginUser = UserBean.userController.getUserByUuid(userUuidFromLogin);
    private final static Properties properties = LoadingFileData.loadingProperties();
    private final static ResponseUserDto currentUser = UserBean.userController
            .getUserByUuid(String.valueOf(WriteDataForVerifyLoginStatus.isLogin()));
    private static int serverPort;
    private static String serverIpAddress;

    static {
        assert properties != null;
        serverPort = Integer.parseInt(properties.getProperty("server_port"));
        serverIpAddress = GetMachineIP.getMachineIP();
    }
    private static void pressToNext(){
        System.out.print("> Press enter to continue: ");
        new Scanner(System.in).nextLine();
    }
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
                System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
                // get all connected user
                List<ResponseUserDto> connectedUsers = ChatConnectionBean.controller
                        .getAllConnectedUser(currentUser.uuid());
                if(connectedUsers.isEmpty()){
                    System.out.println("""
                ===================================
                  You have no connection yet :( !
                ===================================""");
                }else {
                    UserViewTable.getTable(connectedUsers);
                    System.out.println("---");
                    System.out.print("[+] Enter connector name to chat: ");
                    String cName = new Scanner(System.in).nextLine();
                    ResponseUserDto conn = UserBean.userController.getUserByName(cName);
//                    if(conn!=null){
//                        new Client().getClientChatSocket(serverIpAddress, serverPort);
//                    }
                    new Client().getClientChatSocket(serverIpAddress, serverPort, currentUser.name() ,conn);

                    System.out.println("----");
                }
                pressToNext();
            }case 2 ->{
                System.out.println("----");
                System.out.print("[+] Insert connector name: ");
                String username = new Scanner(System.in).nextLine();
                // start adding connection
                ResponseUserDto userWantedToConnect = UserBean.userController.getUserByName(username);
                if(userWantedToConnect!=null){
                    System.out.print("[*] Are you sure you want to connect with " + userWantedToConnect.name() + " ? (y/n): ");
                    String answer = new Scanner(System.in).nextLine();
                    if(answer.equals("y")){
                        int result = ChatConnectionBean.controller.createConnection(CreateChatConnectionUsingUserNameDto.builder()
                                .loginUserName(loginUser.name())
                                .wantedToConnectedUserName(userWantedToConnect.name())
                                .build());
                        System.out.println(":) Now, you have connected with [ " + userWantedToConnect.name() + " ]");
                        pressToNext();
                    }
                }else {
                    System.out.println("[!] User you wanted to connection is not found :(");
                }
            }
            case 3->{
                System.out.println("----");

                System.out.println("----");
            }
            case 4 ->{
                System.out.print("[*] Are you sure you want to logout? (y/n): ");
                String answer = new Scanner(System.in).nextLine();
                if(answer.equals("y")){
                    WriteDataForVerifyLoginStatus.writeDataOfStatusToFile("null");
                    UIWithoutAccount.home();
                }
                pressToNext();
            }
            case 5 ->{
                System.out.print("[*] Are you sure you want to exit? (y/n): ");
                String answer = new Scanner(System.in).nextLine();
                if(answer.equals("y")){
                    System.out.println("\uD83D\uDC4B Back to Home");
                    System.exit(0);
                }
            }
        }

    }
}
