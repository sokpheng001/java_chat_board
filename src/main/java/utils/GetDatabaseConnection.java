package utils;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Properties;

public class GetDatabaseConnection {
    private final static Properties properties  = LoadingFileData.loadingProperties();

    public static Connection getConnection(){
        try{
            assert properties != null;
            return DriverManager.getConnection(properties.getProperty("db_url"),
                    properties.getProperty("db_user"),
                    properties.getProperty("db_password"));
        }catch (Exception exception){
            System.out.println("Connection Failed! Check output console: " + exception.getMessage());
        }
        return null;
    };
}
