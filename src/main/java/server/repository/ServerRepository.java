package server.repository;


import model.dto.ServerRespondDto;
import utils.GetDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class ServerRepository {
    private static int truncateDataInServerTable(){
        String sql = "TRUNCATE TABLE servers RESTART IDENTITY CASCADE";
        try(Connection connection = GetDatabaseConnection.getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return 1;
        } catch (Exception exception){
            System.out.println("[!] Problem during truncate data from server table: " + exception.getMessage());
        }
        return 0;
    }
    public static int insertServerIPAddressToDbAndReturnServerRowId(String ipAddress, Integer port){
        truncateDataInServerTable();
        String sql = "INSERT INTO servers (ip_address, port) " +
                "VALUES (?, ?) RETURNING id";
        try {

            try(Connection connection  = GetDatabaseConnection.getConnection()) {
                assert connection != null;
                try(PreparedStatement stmt = connection.prepareStatement(sql)){
                    // Set parameters for the prepared statement
                    stmt.setString(1, ipAddress);
                    stmt.setInt(2, port);

                    // Execute query and get the auto-generated ID
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        return Math.toIntExact(rs.getLong("id"));
                    }
                }
            }
        } catch (Exception exception){
            System.out.println("[!] Error while inserting chat server info to database: " + exception.getMessage());
        }
        return 0;
    }
    public static ServerRespondDto findFirstServerRowData(){
        String sql = "SELECT * FROM servers";
        try (Connection connection = GetDatabaseConnection.getConnection()) {
            assert connection != null;
            try (Statement statement  =connection.createStatement()) {
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    return ServerRespondDto.builder()
                            .id(rs.getInt("id"))
                            .ipAddress(rs.getString("ip_address"))
                            .port(rs.getInt("port"))
                            .build();
                }
            }
        } catch (Exception e) {
            System.out.println("[!] Error finding chat server info at first row " + e.getMessage());
        }
        return null;
    }
    public static ServerRespondDto findSeverById(Integer id){
        String sql = "SELECT * FROM servers WHERE id = ?";
        try (Connection connection = GetDatabaseConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return ServerRespondDto.builder()
                            .id(rs.getInt("id"))
                            .ipAddress(rs.getString("ip_address"))
                            .port(rs.getInt("port"))
                            .build();
                }
            }
        } catch (Exception e) {
            System.out.println("[!] Error finding chat server info by id: " + e.getMessage());
        }
        return null;
    }
}
