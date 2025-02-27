package repository;


import model.User;
import repository.abstraction.UserRepository;
import utils.GetDatabaseConnection;

import java.sql.*;
import java.util.List;


import static utils.LoadingFileData.properties;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public int save(User user) {
        String sql = "INSERT INTO users (uuid, username, email, password, is_deleted, is_verified) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
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
