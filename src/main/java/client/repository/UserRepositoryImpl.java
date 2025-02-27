package client.repository;


import model.User;
import client.repository.abstraction.UserRepository;
import utils.GetDatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public int save(User user) {
        String sql = "INSERT INTO users (uuid, user_name, email, password, is_deleted, is_verified,created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?,?) RETURNING id";
        try {

            try(Connection connection  = GetDatabaseConnection.getConnection()) {
                assert connection != null;
                try(PreparedStatement stmt = connection.prepareStatement(sql)){
                    // Set parameters for the prepared statement
                    stmt.setString(1, user.getUuid());
                    stmt.setString(2, user.getUserName());
                    stmt.setString(3, user.getEmail());
                    stmt.setString(4, user.getPassword());
                    stmt.setBoolean(5, user.getIsDeleted());
                    stmt.setBoolean(6, user.getIsVerified());
                    stmt.setDate(7,Date.valueOf(LocalDate.now()));

                    // Execute query and get the auto-generated ID
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        user.setId(rs.getLong("id"));  // Set the generated id to the user object
                    }
                    return Math.toIntExact(user.getId());
                }
            }
        } catch (Exception exception){
            System.out.println("[!] Error while saving user: " + exception.getMessage());
        }
        return 0;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }
}
