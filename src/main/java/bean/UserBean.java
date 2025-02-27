package bean;

import controller.UserController;
import mapper.UserManualMapper;
import repository.UserRepositoryImpl;
import repository.abstraction.UserRepository;

/**
 * <h>This class is created for manage the User class's object</h>
 * @author kimchansokpheng
 * @version 1
 */
public class UserBean {
    public static UserController userController = new UserController();
    public static UserRepository userRepository = new UserRepositoryImpl();
    public static UserManualMapper userManualMapper = new UserManualMapper();
}
