package utils;

import java.io.FileWriter;

public class WriteDataForVerifyLoginStatus {
    public static void writeDataOfStatusToFile(String userUuid) {
        try(FileWriter fileWriter = new FileWriter("isLogin.txt")) {
            fileWriter.write(userUuid + "\n");
        }catch (Exception exception){
            System.out.println("[!] Error while writing to register file: " + exception.getMessage());
        }
    }
}
