package bean;

import client.controller.UserController;
import mapper.UserManualMapper;

/**
 * <h>This class is created for manage the User class's related object</h>
 * @author Kim Chansokpheng
 * @version 1.0
 */
public class UserBean {
    public static UserController userController = new UserController();
    public static UserManualMapper userManualMapper = new UserManualMapper();
}
