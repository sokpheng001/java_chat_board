package bean;

import controller.UserController;
import mapper.UserManualMapper;
import repository.UserRepositoryImpl;
import repository.abstraction.UserRepository;

public class UserBean {
    public static UserController userController = new UserController();
    public static UserRepository userRepository = new UserRepositoryImpl();
    public static UserManualMapper userManualMapper = new UserManualMapper();
}
