package view;

import java.util.Scanner;

public class UIWithAccount {
    public static void home(){
        System.out.println("""
                -------------------------
                Welcome to the Chat_Board
                -------------------------
                1. View all connection
                2. Add a new connection
                3. Exit
                ----
                """);
        System.out.print("[+] Insert option: ");
        int opt = new Scanner(System.in).nextInt();
    }
    private static void switchOpt(int opt){
        switch (opt){
            case 1 ->{

            }
        }
    }
}
