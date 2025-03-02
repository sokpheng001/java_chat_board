package client.repository;

import client.repository.abstraction.SokPhengRepository;
import model.ChatConnection;
import utils.GetDatabaseConnection;

import java.sql.*;
import java.time.Instant;
import java.util.List;

public class ChatConnectionRepository implements SokPhengRepository<ChatConnection, Integer> {
    @Override
    public Integer save(ChatConnection connection) {
        String sql = "INSERT INTO chat_connections (user1_id, user2_id, uuid, connected_at) VALUES (?, ?, ?,?) RETURNING id";

        try (Connection conn = GetDatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, connection.getUser1Id());
                stmt.setInt(2, connection.getUser2Id());
                stmt.setString(3, connection.getUuid());
                stmt.setDate(4,Date.valueOf(String.valueOf(Instant.now())));

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
}
