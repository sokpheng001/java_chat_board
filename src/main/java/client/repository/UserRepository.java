package client.repository;


import client.repository.abstraction.SokPhengRepository;
import model.User;
import model.dto.ResponseUserDto;
import utils.GetDatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements SokPhengRepository<User, Integer> {

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("uuid"),
                rs.getString("user_name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getDate("login_date"),
                rs.getBoolean("is_deleted"),
                rs.getBoolean("is_verified")
        );
    }

    @Override
    public Integer save(User user) {
        String sql = "INSERT INTO users (uuid, user_name, email, password, is_deleted, is_verified,created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?,?) RETURNING id";
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
        } catch (Exception exception){
            System.out.println("[!] Error while saving user: " + exception.getMessage());
        }
        return 0;
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE user_name = ?";
        try (Connection connection = GetDatabaseConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            System.out.println("[!] Error finding user by username: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";

        List<User> users = new ArrayList<>();
        try (Connection connection = GetDatabaseConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    users.add(extractUserFromResultSet(rs));
                }
            }
        } catch (Exception e) {
            System.out.println("[!] Error fetching all users: " + e.getMessage());
        }
        return users;
    }

    @Override
    public Integer delete(Integer id) {
        return 0;
    }

    @Override
    public Integer update(Integer id) {
        return 0;
    }

    public Integer update(User user) {
        String sql = "UPDATE users SET user_name = ?, email = ?, password = ?, " +
                "is_deleted = ?, is_verified = ?, login_date = ? WHERE uuid = ?";

        try (Connection connection = GetDatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setBoolean(4, user.getIsDeleted());
            stmt.setBoolean(5, user.getIsVerified());
            stmt.setDate(6, new java.sql.Date(user.getLoginDate().getTime()));
            stmt.setString(7, user.getUuid()); // Use UUID as the identifier

            // Execute the update and return the number of affected rows
            return stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("[!] Error while updating user: " + e.getMessage());
        }
        return 0;

    }

    public User findUserByUuid(String uuid) {
        String sql = "SELECT * FROM users WHERE uuid = ?";
        try (Connection connection = GetDatabaseConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, uuid);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {

                    return extractUserFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            System.out.println("[!] Error finding user by uuid: " + e.getMessage());
        }
        return null;
    }
    public User findUserById(Integer id){
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = GetDatabaseConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {

                    return extractUserFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            System.out.println("[!] Error finding user by id: " + e.getMessage());
        }
        return null;
    }
}
