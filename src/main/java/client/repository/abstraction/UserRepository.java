package client.repository.abstraction;

import model.User;

import java.util.List;

public interface UserRepository {
    int save(User user);
    User findByUsername(String username);
    List<User> findAll();
    int update(User user);
}
