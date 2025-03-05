package utils;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class WriteDataForVerifyLoginStatus {
    public static String temporaryCurrenUsername = null;
    public static void writeDataOfStatusToFile(String userUuid) {
        try(BufferedWriter writer= new BufferedWriter(new FileWriter("isLogin.txt")) ) {
//            fileWriter.write(userUuid + "\n");
            writer.write(userUuid);
            writer.flush();// Ensure data is written immediately
        }catch (Exception exception){
            System.out.println("[!] Error while writing to register file: " + exception.getMessage());
        }
    }
    public static StringBuilder isLogin(){
        try(FileReader reader = new FileReader("isLogin.txt")){
            int data;
            StringBuilder uuid = new StringBuilder();
            while ((data=reader.read())!=-1){
//                assert false;
                uuid.append((char) data);

            }
            return uuid;
        }catch (Exception exception){
            System.out.println("[!] Error during check if user has been login: "  + exception.getMessage());
        }
        return null;
    }
}
