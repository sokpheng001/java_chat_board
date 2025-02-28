package client.controller;

import bean.UserBean;
import client.service.UserService;
import client.service.UserServiceImpl;
import model.dto.ResponseUserDto;

public class AuthController {
    private final UserService userService = new UserServiceImpl();
    public ResponseUserDto login(String username, String password) {
        return userService.loginUser(username, password);
    }
}
