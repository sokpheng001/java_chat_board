package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class LoadingFileData {
    public static final Properties properties = new Properties();
    public static Properties loadingProperties(){
        try (BufferedReader reader = new BufferedReader(new FileReader("app.properties"))){
            properties.load(reader);
            return properties;
        }catch (Exception exception){
            System.out.println("Problem during loading properties: " + exception.getMessage());
        }
        return null;
//        System.out.println(properties.getProperty("database_url"));
    }
}
