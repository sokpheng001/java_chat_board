package bean;

import client.controller.UserController;
import client.repository.UserRepository;
import mapper.UserManualMapper;

/**
 * <h>This class is created for manage the User class's related object</h>
 * @author Kim Chansokpheng
 * @version 1.0
 */
public class UserBean {
    public static final UserController userController = new UserController();
    public static final UserManualMapper userManualMapper = new UserManualMapper();
    public static final UserRepository userRepository = new UserRepository();
}
