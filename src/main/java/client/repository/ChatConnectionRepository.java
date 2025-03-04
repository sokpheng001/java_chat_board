package client.repository;

import bean.UserBean;
import client.repository.abstraction.SokPhengRepository;
import model.ChatConnection;
import model.User;
import utils.GetDatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChatConnectionRepository implements SokPhengRepository<ChatConnection, Integer> {
    @Override
    public Integer save(ChatConnection connection) {
        String sql = "INSERT INTO chat_connections (login_user_id, wanted_to_connect_user_id, uuid, connected_at) VALUES (?, ?, ?,?) RETURNING id";

        try (Connection conn = GetDatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setLong(1, connection.getLoginUserId());
                stmt.setLong(2, connection.getWantedToConnectUserId());
                stmt.setString(3, connection.getUuid());
                stmt.setDate(4,Date.valueOf(LocalDate.now()));

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);  // Return the generated ID
                    }
                }

            }
        } catch (SQLException e) {
            System.out.println("[!] Error during inserting data to chat connection table: "  +e.getMessage());
        }
        return null;  // Return null if insertion fails
    }

    @Override
    public List<ChatConnection> findAll() {
        return List.of();
    }

    @Override
    public Integer delete(Integer id) {
        return 0;
    }

    @Override
    public Integer update(Integer id) {
        return 0;
    }
    // define new method beyond from interface
    public List<User> getAllConnectedUserByUserLoginId(Integer id){
        String sql = """
                SELECT * FROM chat_connections WHERE login_user_id = ?
                """;
        try(Connection connection = GetDatabaseConnection.getConnection()) {
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                // set id for search
                preparedStatement.setInt(1,id);
                //
                ResultSet resultSet = preparedStatement.executeQuery();

                List<User> connectedUsers = new ArrayList<>();
                while (resultSet.next()){
                    connectedUsers.add(UserBean.userRepository.findUserById(resultSet.getInt("wanted_to_connect_user_id")));
                }
                return connectedUsers;

            }
        } catch (Exception exception){
            System.out.println("[!] Problem during get all connected users in chat connection: " + exception.getMessage());
        }
        return null;
    }
}
