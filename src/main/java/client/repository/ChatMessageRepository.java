package client.repository;

import client.repository.abstraction.SokPhengRepository;
import model.ChatMessage;
import utils.GetDatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageRepository implements SokPhengRepository<ChatMessage, Integer> {
    @Override
    public Integer save(ChatMessage chatMessage) {
        String sql = "INSERT INTO chat_messages (sender_id, receiver_id, message, uuid, sent_at) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection connection = GetDatabaseConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, chatMessage.getSenderId());
                stmt.setInt(2, chatMessage.getReceiverId());
                stmt.setString(3, chatMessage.getMessage());
                stmt.setString(4, chatMessage.getUuid());
                stmt.setDate(5, chatMessage.getSentAt());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (Exception exception) {
            System.out.println("[!] Error while saving chat message: " + exception.getMessage());
        }
        return 0;
    }

    @Override
    public List<ChatMessage> findAll() {
        String sql = "SELECT id, sender_id, receiver_id, message, uuid, sent_at FROM chat_messages";
        List<ChatMessage> chatMessages = new ArrayList<>();
        try (Connection connection = GetDatabaseConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    chatMessages.add(new ChatMessage(
                            rs.getInt("id"),
                            rs.getString("uuid"),
                            rs.getInt("sender_id"),
                            rs.getInt("receiver_id"),
                            rs.getString("message"),
                            rs.getDate("sent_at")
                    ));
                }
            }
        } catch (Exception exception) {
            System.out.println("[!] Error while retrieving chat messages: " + exception.getMessage());
        }
        return chatMessages;
    }

    @Override
    public Integer delete(Integer id) {
        String sql = "DELETE FROM chat_messages WHERE id = ?";

        try (Connection connection = GetDatabaseConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                return stmt.executeUpdate();
            }
        } catch (Exception exception) {
            System.out.println("[!] Error while deleting chat message: " + exception.getMessage());
        }
        return 0;
    }

    @Override
    public Integer update(Integer id) {
        return 0;
    }
}
